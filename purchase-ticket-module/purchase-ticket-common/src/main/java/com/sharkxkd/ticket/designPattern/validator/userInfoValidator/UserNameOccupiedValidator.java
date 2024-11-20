package com.sharkxkd.ticket.designPattern.validator.userInfoValidator;

import com.sharkxkd.ticket.dto.UserRegisterDTO;
import org.springframework.stereotype.Component;

/**
 * 用户名被占用校验器
 *
 * @author zc
 * @date 2024/11/19 17:04
 **/
@Component
public class UserNameOccupiedValidator implements UserInfoValidator<UserRegisterDTO>{
    @Override
    public void validate(UserRegisterDTO userRegisterDTO) {
        System.out.println("正在执行用户名校验");
    }
}
