package com.sharkxkd.ticket.enums.errorEnum;

import com.sharkxkd.ticket.enums.errorEnum.AbstractResultStatusEnum;

/**
 * 错误码汇总
 *
 * @author zc
 * @date 2024/11/26 22:05
 **/

public enum ErrorCodeEnum implements AbstractResultStatusEnum {
    SERVICE_ERROR(10000,"业务异常");
    private Integer code;
    private String message;

    ErrorCodeEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
