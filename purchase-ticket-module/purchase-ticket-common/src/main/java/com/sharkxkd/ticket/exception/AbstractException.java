package com.sharkxkd.ticket.exception;

import com.sharkxkd.ticket.enums.errorEnum.AbstractResultStatusEnum;
import lombok.Getter;

/**
 * 抽象异常
 *
 * @author zc
 * @date 2024/11/26 21:51
 **/
@Getter
public abstract class AbstractException extends RuntimeException{
    private Integer code;
    private String message;

    public AbstractException(String message, Throwable cause, AbstractResultStatusEnum abstractResultStatusEnum) {
        super(message, cause);
        this.code = abstractResultStatusEnum.getCode();
        this.message = abstractResultStatusEnum.getMessage();
    }
}
