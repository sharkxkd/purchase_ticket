package com.sharkxkd.ticket.dto;

import com.sharkxkd.ticket.enums.IdTypeEnum;
import lombok.Data;

/**
 * 用户注册的dto类
 *
 * @author zc
 * @date 2024/11/20 20:18
 **/
@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String realName;
    private IdTypeEnum idType;
    private String idCard;
    private String phone;
    private String mail;
}
