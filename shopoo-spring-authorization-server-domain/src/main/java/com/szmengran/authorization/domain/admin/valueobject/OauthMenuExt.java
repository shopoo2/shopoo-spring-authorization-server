package com.szmengran.authorization.domain.admin.valueobject;

import com.szmengran.authorization.domain.admin.entity.OauthMenu;
import lombok.Data;

/**
 * @Author MaoYuan.Li
 * @Date 2023/2/10 16:26
 * @Version 1.0
 */
@Data
public class OauthMenuExt extends OauthMenu {
    
    /**
     * 角色ID
     */
    private Long roleId;
}
