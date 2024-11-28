package com.sharkxkd.ticket.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 身份认证类型枚举类
 *
 * @author zc
 * @date 2024/11/20 20:30
 **/

public enum IdTypeEnum {
    /**
     * 居民身份证
     */
    ID_CARD(0,"居民身份证"),
    /**
     * 港澳居民居住证
     */
    HM_RESIDENT_CARD(1,"港澳居民居住证"),
    /**
     * 台湾居民居住证
     */
    TW_RESIDENT_CARD(2,"台湾居民居住证"),
    /**
     * 外国永久居留身份证
     */
    FOREIGN_RESIDENT_CARD(3,"外国永久居留身份证"),
    /**
     * 外国护照
     */
    FOREIGN_PASSPORT(4,"外国护照"),
    /**
     * 中国护照
     */
    CHINA_PASSPORT(5,"中国护照"),
    /**
     * 港澳居民来往内地通行证
     */
    HM_PASS_CARD(6,"港澳居民来往内地通行证"),
    /**
     * 台湾居民来往内地通行证
     */
    TW_PASS_CARD(7,"台湾居民来往内地通行证");
    @EnumValue
    private Integer code;
    private String desc;
    IdTypeEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
