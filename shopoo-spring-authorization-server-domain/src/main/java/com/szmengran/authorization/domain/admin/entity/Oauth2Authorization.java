package com.szmengran.authorization.domain.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;

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
public class Oauth2Authorization implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String registeredClientId;

    private String principalName;

    private String authorizationGrantType;

    private String authorizedScopes;

    private Blob attributes;

    private String state;

    private Blob authorizationCodeValue;

    private LocalDateTime authorizationCodeIssuedAt;

    private LocalDateTime authorizationCodeExpiresAt;

    private Blob authorizationCodeMetadata;

    private Blob accessTokenValue;

    private LocalDateTime accessTokenIssuedAt;

    private LocalDateTime accessTokenExpiresAt;

    private Blob accessTokenMetadata;

    private String accessTokenType;

    private String accessTokenScopes;

    private Blob oidcIdTokenValue;

    private LocalDateTime oidcIdTokenIssuedAt;

    private LocalDateTime oidcIdTokenExpiresAt;

    private Blob oidcIdTokenMetadata;

    private Blob refreshTokenValue;

    private LocalDateTime refreshTokenIssuedAt;

    private LocalDateTime refreshTokenExpiresAt;

    private Blob refreshTokenMetadata;


}
