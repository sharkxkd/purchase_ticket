package com.sharkxkd.ticket.enums;

/**
 * 校验器名字枚举类
 *
 * @author zc
 * @date 2024/11/19 17:01
 **/
public enum ValidatorEnum {
    /**
     * 用户信息校验器
     */
    USERINFO_VALIDATOR("USERINFO_VALIDATOR");
    private String validatorName;
    ValidatorEnum(String validatorName){
        this.validatorName = validatorName;
    }
}
