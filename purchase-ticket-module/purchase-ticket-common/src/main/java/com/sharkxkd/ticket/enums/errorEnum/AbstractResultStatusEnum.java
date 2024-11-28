package com.sharkxkd.ticket.enums.errorEnum;

/**
 * 返回信息抽象枚举类
 *
 * @author zc
 * @date 2024/11/19 20:17
 **/
public interface AbstractResultStatusEnum {
    /**
     * 获取信息的码
     * @return
     */
    Integer getCode();

    /**
     * 获取信息提示信息
     * @return
     */
    String getMessage();
//    /**
//     * 成功枚举类
//     */
//    SUCCESS(200, "成功"),
//    /**
//     * 成功枚举类
//     */
//    FAIL(-1, "失败"),
//    /**
//     * 成功枚举类
//     */
//    INTERNAL_SERVER_ERROR(10001, "服务器内部错误"),
//    /**
//     * 成功枚举类
//     */
//    SERVICE_EXCEPTION(10002,"业务异常"),
//
//    ARTICLE_NOT_FOUND(20007,"文章不存在");
//    private int code = 0;
//    private String message;
//    ResultStatusEnum(int code, String message) {
//        this.code = code;
//        this.message = message;
//    }
}
