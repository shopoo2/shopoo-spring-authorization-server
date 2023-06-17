package com.szmengran.authorization.domain.admin.valueobject;

import com.szmengran.authorization.domain.admin.entity.Oauth2User;
import lombok.Data;

/**
 * @Author MaoYuan.Li
 * @Date 2023/2/10 16:26
 * @Version 1.0
 */
@Data
public class Oauth2UserExt extends Oauth2User {
    
    /**
     * 角色主键
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    
}
