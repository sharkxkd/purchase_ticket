package com.sharkxkd.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sharkxkd.ticket.constant.RedisConstants;
import com.sharkxkd.ticket.dao.UserMailMapper;
import com.sharkxkd.ticket.dao.UserMapper;
import com.sharkxkd.ticket.dao.UserPhoneMapper;
import com.sharkxkd.ticket.designPattern.validator.ValidatorChainHandler;
import com.sharkxkd.ticket.dto.UserLoginDTO;
import com.sharkxkd.ticket.dto.UserRegisterDTO;
import com.sharkxkd.ticket.entity.*;
import com.sharkxkd.ticket.enums.ValidatorEnum;
import com.sharkxkd.ticket.exception.ServiceException;
import com.sharkxkd.ticket.service.UserService;
import com.sharkxkd.ticket.util.JWTUtils;
import com.sharkxkd.ticket.util.BeanUtil;
import com.sharkxkd.ticket.util.PasswordUtils;
import com.sharkxkd.ticket.util.RedisLock;
import com.sharkxkd.ticket.vo.UserLoginVO;
import com.sharkxkd.ticket.vo.UserRegisterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

import static com.sharkxkd.ticket.enums.errorEnum.UserStatusEnum.*;

/**
 * 用户业务层具体实现
 *
 * @author zc
 * @date 2024/11/13 22:09
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RedisBloomFilter redisBloomFilter;
    private final ValidatorChainHandler<UserRegisterDTO> validatorChainHandler;
    private final StringRedisTemplate stringRedisTemplate;
    private final UserPhoneMapper userPhoneMapper;
    private final UserMailMapper userMailMapper;

    /**
     * 检测用户名是否被占用的实现类
     * 1.直接查询数据库，判断是否在对应的用户名 》》 压力全部给到数据库，数据量会弄垮数据库
     * 2.为用户名字段加上索引，若用户量较大可以考虑分库分表，通过哈希去缩小查询规模
     * 3.使用布隆过滤器，判断一个用户名是否被占用，但是存在误判的可能性，并且最大的缺陷为不能支持删除操作
     * 考虑到用户可以随时对用户名进行修改，布隆过滤器虽然查询快，效率高，但是可能会误差不断增大影响用户的体验。
     * 4.布谷鸟过滤器，支持删除操作
     * 5.布隆过滤器的计数版，不使用单纯的0和1来存储哈希结果，而是对结果进行计数，导致整个空间会增大。
     * 这里实现为名字加上索引，对数据库进行调优
     * @param username  传入需要判断的用户名
     * @return          返回判断结果
     */
    @Override
    public boolean validateUserNameDuplicated(String username) {
        boolean containsUsername = redisBloomFilter.mightContain(RedisConstants.USERNAME_BLOOM_KEY,username);
        if(containsUsername){
            //对数据库进行一次查询，判断是否真的存在
            log.info("该用户名已存在，对应用户名为：{}",username);
            //TODO:对username字段增加索引
            QueryWrapper<UserDO> queryWrapper = new QueryWrapper<UserDO>();
            queryWrapper.eq("username",username);
            UserDO userDO = userMapper.selectOne(queryWrapper);
            return userDO == null;
        }
        //若没有，则一定是不存在，所以直接返回可以使用
        return true;
    }

    /**
     * 用户信息注册
     * 1.接收前端请求数据，根据上游数据不可信原则还需要对数据进行依次校验
     * 2.是否为并发执行该接口？是否存在执行该接口的时候插入一条重复的用户名？若涉及到并发执行，是否应该考虑出问题如何恢复——事务
     * 3.加锁解决上述情况，并且要同时按顺序对三张表操作，依次为用户表，邮箱表，手机号表
     * @param userRegisterDTO   用户注册信息类
     * @return                  前端展示信息，数据回填
     */
    @Override
    public UserRegisterVO register(UserRegisterDTO userRegisterDTO) {
        validatorChainHandler.validate(ValidatorEnum.USERINFO_VALIDATOR,userRegisterDTO);
        RedisLock lock = new RedisLock(stringRedisTemplate,userRegisterDTO.getUsername());
        boolean enable = lock.tryLock(2L);
        if(!enable){
            //抛出用户名被占用的异常
            throw new ServiceException(USERNAME_DUPLICATED);
        }
        try{
            //分开捕获异常，重定义为业务异常交给全局处理,用户名为索引，手机号邮箱为索引
            UserDO userDO = BeanUtil.convert(userRegisterDTO, UserDO.class);
            userDO.setSalt(PasswordUtils.generateSalt());
            try{
                userDO.setPassword(PasswordUtils.encryptPassword(userDO.getPassword(),userDO.getSalt()));
            }catch (NoSuchAlgorithmException e){
                log.error("加密密码出现错误",e);
                throw new ServiceException("加密密码出现错误，加密算法不存在");
            }
            try{
                userMapper.insert(userDO);
            }catch (DuplicateKeyException e){
                log.error("用户名索引不唯一{}",userRegisterDTO.getUsername());
                throw new ServiceException(USERNAME_DUPLICATED);
            }
            try{
                userPhoneMapper.insert(BeanUtil.convert(userRegisterDTO, PhoneDO.class));
            }catch (DuplicateKeyException e){
                log.error("电话索引不唯一{}",userRegisterDTO.getPhone());
                throw new ServiceException(TELEPHONE_DUPLICATED);
            }
            try{
                userMailMapper.insert(BeanUtil.convert(userRegisterDTO, MailDO.class));
            }catch (DuplicateKeyException e){
                log.error("邮箱索引不唯一{}",userRegisterDTO.getMail());
                throw new ServiceException(MAIL_DUPLICATED);
            }
            //更新布隆过滤器
            redisBloomFilter.put(RedisConstants.USERNAME_BLOOM_KEY, userRegisterDTO.getUsername());
        }finally {
            lock.unLock();
        }
        return BeanUtil.convert(userRegisterDTO, UserRegisterVO.class);
    }

    /**
     * 通过不同的格式判断选择的是用户名登录还是手机号登录还是对应的邮箱登录
     * 1.判断是否是邮箱格式，去对应的邮箱表寻找对应的用户
     * 2.判断是否是手机号格式，去对应的收集表寻找对应的用户
     * 3.根据上述查询出来的用户名，去寻找对应的密码进行校验对比
     * 4.若没有找到对应的用户名就直接使用该信息即可
     * @param userLoginDTO  用户登录信息类
     * @return
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        String loginInfo = userLoginDTO.getUsernameOrMailOrPhone();
        String username = null;
        if(loginInfo.contains("@")){
            LambdaQueryWrapper<MailDO> mailDoQueryWrapper = Wrappers.lambdaQuery(MailDO.class);
            mailDoQueryWrapper.eq(MailDO::getMail,loginInfo);
            MailDO mailDO = userMailMapper.selectOne(mailDoQueryWrapper);
            if(mailDO == null){
                throw new ServiceException(USER_NOT_FOUND);
            }
            username = mailDO.getUsername();
        }else{
            //查询是否为手机号
            LambdaQueryWrapper<PhoneDO> phoneDoQueryWrapper = Wrappers.lambdaQuery(PhoneDO.class);
            phoneDoQueryWrapper.eq(PhoneDO::getPhone,loginInfo);
            PhoneDO phoneDO = userPhoneMapper.selectOne(phoneDoQueryWrapper);
            if(phoneDO != null){
                username = phoneDO.getUsername();
            }
        }
        if(username == null){
            username = loginInfo;
        }
        LambdaQueryWrapper<UserDO> userDoQueryWrapper = Wrappers.lambdaQuery(UserDO.class);
        userDoQueryWrapper.eq(UserDO::getUsername,username);
        UserDO userDO = userMapper.selectOne(userDoQueryWrapper);
        if(userDO == null){
            throw new ServiceException(USER_NOT_FOUND);
        }
        try{
            boolean isMatch = PasswordUtils.verifyPassword(userLoginDTO.getPassword(),userDO.getSalt(),userDO.getPassword());
            if(!isMatch){
                throw new ServiceException(PASSWORD_ERROR);
            }
        }catch (NoSuchAlgorithmException e){
            log.error("解密密码出现错误",e);
            throw new ServiceException("解密密码出现错误，加密算法不存在");
        }
        //token创建
        TokenMsg tokenMsg = BeanUtil.convert(userDO, TokenMsg.class);
        UserLoginVO userLoginVO = BeanUtil.convert(userDO,UserLoginVO.class);
        userLoginVO.setToken(JWTUtils.createToken(tokenMsg));
        return userLoginVO;
    }
}
