package com.szmengran.authorization.domain.admin.valueobject;

import com.szmengran.authorization.domain.admin.entity.OauthRole;
import lombok.Data;

/**
 * @Author MaoYuan.Li
 * @Date 2023/2/10 16:16
 * @Version 1.0
 */
@Data
public class OauthRoleExt extends OauthRole {
    /**
     * 用户ID
     */
    private String userId;
}
