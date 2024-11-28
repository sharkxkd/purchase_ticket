package com.sharkxkd.ticket.dto;

import com.sharkxkd.ticket.enums.IdTypeEnum;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 用户注册的dto类
 *
 * @author zc
 * @date 2024/11/20 20:18
 **/
@Data
public class UserRegisterDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "真实姓名不能为空")
    private String realName;
    @NotNull(message = "身份类型不能为空")
    private IdTypeEnum idType;
    @NotBlank(message = "身份号码不能为空")
    private String idCard;
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-79])|(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[01256789]))\\d{8}$",message = "手机号不符合规则")
    private String phone;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱不符合规范")
    private String mail;
}
