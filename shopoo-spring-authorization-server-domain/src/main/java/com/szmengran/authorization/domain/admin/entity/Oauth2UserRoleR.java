package com.szmengran.authorization.domain.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (OauthUserRoleR)实体类
 *
 * @author makejava
 * @since 2021-03-12 14:36:46
 */
@Data
public class Oauth2UserRoleR implements Serializable {
    private static final long serialVersionUID = -58448603267079691L;
    /**
     * 角色主键
     */
    private Long roleId;
    /**
     * 用户主键
     */
    private String userId;

}
