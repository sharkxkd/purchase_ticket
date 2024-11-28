package com.sharkxkd.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录的DTO类，接收前端请求参数
 *
 * @author zc
 * @date 2024/11/27 21:06
 **/
@Data
public class UserLoginDTO {
    @NotBlank
    private String usernameOrMailOrPhone;
    @NotBlank
    private String password;
}
