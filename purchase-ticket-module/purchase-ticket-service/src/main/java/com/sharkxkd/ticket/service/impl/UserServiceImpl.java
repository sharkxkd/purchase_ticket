package com.sharkxkd.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sharkxkd.ticket.constant.RedisConstants;
import com.sharkxkd.ticket.dao.UserMapper;
import com.sharkxkd.ticket.designPattern.validator.ValidatorChainHandler;
import com.sharkxkd.ticket.dto.UserRegisterDTO;
import com.sharkxkd.ticket.entity.RedisBloomFilter;
import com.sharkxkd.ticket.entity.UserDO;
import com.sharkxkd.ticket.enums.ValidatorEnum;
import com.sharkxkd.ticket.service.UserService;
import com.sharkxkd.ticket.vo.UserRegisterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
            QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",username);
            UserDO userDO = userMapper.selectOne(queryWrapper);
            return userDO == null;
        }
        //若没有，则一定是不存在，所以直接对bitmap进行更新
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

        return null;
    }
}
