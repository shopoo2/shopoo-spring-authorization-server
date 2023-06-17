package com.szmengran.authorization.domain.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (OauthRoleMenuR)实体类
 *
 * @author makejava
 * @since 2021-05-12 10:29:24
 */
@Data
public class Oauth2RoleMenuR implements Serializable {
    private static final long serialVersionUID = 648989955208978407L;
    /**
     * 菜单表_菜单ID
     */
    private Long menuId;
    /**
     * 角色主键
     */
    private Long roleId;
}
