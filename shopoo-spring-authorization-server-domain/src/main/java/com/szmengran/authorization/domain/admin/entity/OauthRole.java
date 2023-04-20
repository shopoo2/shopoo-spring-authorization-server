package com.szmengran.authorization.domain.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表(OauthRole)实体类
 *
 * @author makejava
 * @since 2020-05-23 16:41:57
 */
@Data
public class OauthRole implements Serializable {
    private static final long serialVersionUID = 617956533073032925L;
    /**
     * 角色主键
     */
    @TableId
    private Long roleId;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态（0-无效，1-有效）
     */
    private Short status;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
}