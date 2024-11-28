package com.sharkxkd.ticket.entity;

import lombok.Data;

/**
 * TokenMsg
 * 用于存储token信息
 * 如何解决用户更改密码之后，token信息不会更新的问题？ 存储一个使用md5后的密码对用户名进行md5，后续比较
 */
@Data
public class TokenMsg {
    private Integer id;
    private String username;
    private String realName;
}
