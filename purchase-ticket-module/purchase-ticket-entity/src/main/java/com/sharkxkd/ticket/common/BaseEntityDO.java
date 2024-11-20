package com.sharkxkd.ticket.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 抽象数据接口实体类
 *
 * @author zc
 * @date 2024/11/13 21:40
 **/
@Data
public class BaseEntityDO {
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 删除标志
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;
}
