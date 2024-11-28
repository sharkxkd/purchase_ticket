package com.sharkxkd.ticket.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sharkxkd.ticket.common.BaseEntityDO;
import lombok.Data;

/**
 * 电话实体类
 *
 * @author zc
 * @date 2024/11/24 14:18
 **/
@Data
@TableName("t_phone")
public class PhoneDO extends BaseEntityDO {
    private Long id;
    private String username;
    private String phone;
    private Long deletionTime;
}
