package com.szmengran.authorization.domain.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (OauthMenu)实体类
 *
 * @author makejava
 * @since 2021-03-12 14:36:39
 */
@Data
public class Oauth2Menu implements Serializable {
    private static final long serialVersionUID = -61107333405120393L;
    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;
    /**
     * 父菜单
     */
    private Long parentId;
    /**
     * 菜单名
     */
    private String name;
    /**
     * 路径
     */
    private String url;
    /**
     * 层级
     */
    private Short level;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Short sort;
    /**
     * 状态 0-无效 1-有效
     */
    private Short status;
    /**
     * 类型 0-资源 1-菜单
     */
    private Short type;
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

}
