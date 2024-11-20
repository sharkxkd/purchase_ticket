package com.sharkxkd.ticket.designPattern.validator;

/**
 * 抽象校验类
 *
 * @author zc
 * @date 2024/11/19 15:29
 **/

public interface AbstractValidator<T> {
    /**
     * 校验方法
     * @param validateData 待校验数据
     */
    void validate(T validateData);

    /**
     * 获取校验器名称
     * @return  返回校验器名称
     */
    String attainValidatorName();

}
