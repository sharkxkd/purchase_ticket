package com.sharkxkd.ticket.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sharkxkd.ticket.common.BaseEntityDO;
import lombok.Data;

/**
 * 邮箱实体类
 *
 * @author zc
 * @date 2024/11/24 14:18
 **/
@Data
@TableName("t_mail")
public class MailDO extends BaseEntityDO {
    private Long id;
    private String username;
    private String mail;
    private Long deletionTime;
}
