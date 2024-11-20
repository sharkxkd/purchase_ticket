package com.sharkxkd.ticket.designPattern.validator.userInfoValidator;

import com.sharkxkd.ticket.dto.UserRegisterDTO;
import org.springframework.stereotype.Component;

/**
 * 用户信息空值校验器
 *
 * @author zc
 * @date 2024/11/19 17:06
 **/
@Component
public class UserInfoMissValidator implements UserInfoValidator<UserRegisterDTO> {
    @Override
    public void validate(UserRegisterDTO userRegisterDTO) {
        System.out.println("用户信息校验类正在执行");
    }
}
