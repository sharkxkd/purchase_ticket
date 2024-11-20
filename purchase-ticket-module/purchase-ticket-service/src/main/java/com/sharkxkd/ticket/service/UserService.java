package com.sharkxkd.ticket.service;

import com.sharkxkd.ticket.dto.UserRegisterDTO;
import com.sharkxkd.ticket.vo.UserRegisterVO;

/**
 * 用户类业务层接口
 *
 * @author zc
 * @date 2024/11/13 22:09
 **/
public interface UserService {
    /**
     * 检测用户名是否被占用
     * @param username  用户名
     * @return          用户名是否存在的判断结果
     */
    boolean validateUserNameDuplicated(String username);

    /**
     * 用户注册业务层
     * @param userRegisterDTO   用户注册信息类
     * @return                  信息展示类
     */
    UserRegisterVO register(UserRegisterDTO userRegisterDTO);
}
