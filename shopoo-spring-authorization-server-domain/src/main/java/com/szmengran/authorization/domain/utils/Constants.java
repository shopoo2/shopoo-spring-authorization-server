package com.szmengran.authorization.domain.utils;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 10:19
 * @Version 1.0
 */
public class Constants {
    
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String GRANT_TYPE_KEY = "grant_type";
    
    public static final String SCOPE_KEY = "scope";


    /**
     * AppID(小程序ID)
     */
    public static final String APPID = "appId";

    /**
     * AppSecret(小程序密钥)
     */
    public static final String SECRET = "secret";

    /**
     * @see <a href=
     * "https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/wx.login.html">开放接口
     * - 登录 - wx.login</a>
     * @see <a href=
     * "https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">登录
     * - code2Session</a>
     *
     * @see OAuth2ParameterNames#CODE
     */
    public static final String CODE = "code";

    /**
     * 用户唯一标识
     *
     * @see <a href=
     * "https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">登录
     * - code2Session</a>
     */
    public static final String OPENID = "openid";

    /**
     * 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 <a href=
     * "https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html">UnionID
     * 机制说明</a>。
     */
    public static final String UNIONID = "unionid";

    /**
     * 会话密钥
     *
     * @see <a href=
     * "https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html">登录
     * - code2Session</a>
     */
    public static final String SESSION_KEY = "sessionKey";
}
