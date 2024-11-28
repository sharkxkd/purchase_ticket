package com.sharkxkd.ticket.entity;

import com.sharkxkd.ticket.enums.errorEnum.AbstractResultStatusEnum;
import com.sharkxkd.ticket.exception.AbstractException;
import lombok.Data;

/**
 * 数据返回前端的封装对象
 *
 * @author zc
 * @date 2024/11/19 20:10
 **/
@Data
public class JsonResult<T> {
    private Integer code;
    private String message;
    private T data;
    private static final Integer SUCCESS_CODE = 200;
    private static final String SUCCESS_MESSAGE = "成功";

    /**
     * 返回成功的数据，默认的响应成功码200，默认提示信息"成功"
     * @param data  返回的数据
     * @return      封装后的返回对象
     * @param <T>   返回的类型
     */
    public static <T> JsonResult<T> success(T data){
        return JsonResult.success(data,SUCCESS_MESSAGE);
    }
    /**
     * 返回成功的数据，默认的响应成功码200,携带自定义成功消息
     * @param data  返回的数据
     * @return      封装后的返回对象
     * @param <T>   返回的类型
     */
    public static <T> JsonResult<T> success(T data,String message){
        JsonResult<T> result = new JsonResult<>();
        result.setCode(SUCCESS_CODE);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 使用错误代码返回
     * @param abstractException 错误的异常类
     * @return  响应对象
     */
    public static <T> JsonResult<T> error(AbstractException abstractException){
        return JsonResult.error(abstractException.getCode(), abstractException.getMessage());
    }

    /**
     *
     * @param code
     * @param message
     * @return
     * @param <T>
     */
    public static <T> JsonResult<T> error(Integer code, String message) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     *
     * @param abstractResultStatusEnum
     * @return
     * @param <T>
     */
    public static <T> JsonResult<T> error(AbstractResultStatusEnum abstractResultStatusEnum) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(abstractResultStatusEnum.getCode());
        result.setMessage(abstractResultStatusEnum.getMessage());
        return result;
    }
}
