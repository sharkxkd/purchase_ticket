package com.sharkxkd.ticket.enums.errorEnum;

import com.sharkxkd.ticket.enums.errorEnum.AbstractResultStatusEnum;
import lombok.Getter;

/**
 * 用户模块返回异常信息枚举类
 *
 * @author zc
 * @date 2024/11/19 20:18
 **/
@Getter
public enum UserStatusEnum implements AbstractResultStatusEnum {
    /**
     * 注册失败
     */
    REGISTER_FAIL(20001,"注册失败"),
    /**
     * 用户名被占用
     */
    USERNAME_DUPLICATED(20002,"用户名已被占用"),
    /**
     * 密码错误
     */
    PASSWORD_ERROR(20003,"密码错误"),
    /**
     * 用户不存在
     */
    USER_NOT_FOUND(20004,"用户不存在"),
    /**
     * 用户凭证信息失效
     */
    TOKEN_EXPIRED(20005,"用户信息失效，请重新登录"),
    /**
     * token解析过程中出现异常
     */
    TOKEN_PARSE_ERROR(20006,"token解析过程中出现异常"),
    /**
     * 手机号被绑定异常
     */
    TELEPHONE_DUPLICATED(20007,"手机号已被绑定"),
    /**
     * 邮箱被绑定异常
     */
    MAIL_DUPLICATED(20008,"邮箱已经被注册过"),
    ;

    private final Integer code;
    private final String message;
    UserStatusEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    @Override
    public Integer getCode(){
        return this.code;
    };

    @Override
    public String getMessage(){
        return this.message;
    };
}
