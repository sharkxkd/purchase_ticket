package com.sharkxkd.ticket.vo;

import lombok.Data;

/**
 * 用户登录VO展示类
 *
 * @author zc
 * @date 2024/11/27 21:08
 **/
@Data
public class UserLoginVO {
    private String username;
    private String realName;
    private String token;
}
