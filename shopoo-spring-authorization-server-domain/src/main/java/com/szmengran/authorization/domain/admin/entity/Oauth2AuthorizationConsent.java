package com.szmengran.authorization.domain.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author maoyuan.li
 * @since 2023-01-13
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Oauth2AuthorizationConsent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "registered_client_id", type = IdType.INPUT)
    private String registeredClientId;

    private String principalName;

    private String authorities;


}
