package com.sharkxkd.ticket.exception;

import com.sharkxkd.ticket.enums.errorEnum.AbstractResultStatusEnum;
import com.sharkxkd.ticket.enums.errorEnum.ErrorCodeEnum;

/**
 * 业务层异常
 *
 * @author zc
 * @date 2024/11/26 21:49
 **/

public class ServiceException extends AbstractException{

    public ServiceException(String message, Throwable cause, AbstractResultStatusEnum abstractResultStatusEnum) {
        super(message == null ? abstractResultStatusEnum.getMessage() : message, cause, abstractResultStatusEnum);
    }

    /**
     * 信息为空直接使用枚举类信息的异常
     * @param abstractResultStatusEnum
     */
    public ServiceException(AbstractResultStatusEnum abstractResultStatusEnum) {
        this(null,abstractResultStatusEnum);
    }

    /**
     * 带信息的异常
     * @param abstractResultStatusEnum  抽象异常枚举类接口
     */
    public ServiceException(String message,AbstractResultStatusEnum abstractResultStatusEnum) {
        this(message,null,abstractResultStatusEnum);
    }
    public ServiceException(String message) {
        this(message, null, ErrorCodeEnum.SERVICE_ERROR);
    }
}
