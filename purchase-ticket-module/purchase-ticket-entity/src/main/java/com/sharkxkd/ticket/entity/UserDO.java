package com.sharkxkd.ticket.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户实体类与数据库一一对应
 *
 * @author zc
 * @date 2024/11/13 21:04
 **/
@Data
@TableName("t_user")
public class UserDO extends BaseEntityDO {
    /**
     * 用户的唯一主键id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户密码对应的盐值
     */
    private String salt;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 国家/地区
     */
    private String region;
    /**
     * 身份类型
     */
    private String idType;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 固定手机号
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 验证状态
     */
    private String verifyStatus;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 地址
     */
    private String address;
    /**
     * 删除时间戳
     */
    private Long deletionTime;

}
