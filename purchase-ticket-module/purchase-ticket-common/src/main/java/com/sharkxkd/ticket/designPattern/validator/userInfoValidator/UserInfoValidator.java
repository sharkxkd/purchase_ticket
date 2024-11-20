package com.sharkxkd.ticket.designPattern.validator.userInfoValidator;

import com.sharkxkd.ticket.dto.UserRegisterDTO;
import com.sharkxkd.ticket.enums.ValidatorEnum;
import com.sharkxkd.ticket.designPattern.validator.AbstractValidator;

/**
 * 用户信息校验接口
 *
 * @author zc
 * @date 2024/11/19 15:33
 **/

public interface UserInfoValidator<T extends UserRegisterDTO> extends AbstractValidator<UserRegisterDTO> {
    /**
     * 获取用户信息校验器名字
     * @return
     */
    @Override
    default String attainValidatorName(){
        return ValidatorEnum.USERINFO_VALIDATOR.name();
    }
}
