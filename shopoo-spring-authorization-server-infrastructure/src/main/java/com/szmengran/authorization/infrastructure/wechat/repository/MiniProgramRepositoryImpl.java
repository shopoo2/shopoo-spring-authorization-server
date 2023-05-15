package com.szmengran.authorization.infrastructure.wechat.repository;

import com.shopoo.wechat.api.WechatFacade;
import com.shopoo.wechat.dto.clientobject.LoginInfoCO;
import com.shopoo.wechat.dto.cqe.LoginCmd;
import com.szmengran.authorization.domain.wechat.repository.MiniProgramRepository;
import com.szmengran.authorization.dto.cqe.WechatMiniProgramQuery;
import com.szmengran.cola.dto.SingleResponse;
import com.szmengran.cola.exception.SysException;
import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 20:40
 * @Version 1.0
 */
@Repository
public class MiniProgramRepositoryImpl implements MiniProgramRepository {
    
    @DubboReference
    private WechatFacade wechatFacade;
    
    @Override
    public LoginInfoCO login(final WechatMiniProgramQuery wechatMiniProgramQuery) {
        LoginCmd loginCmd = LoginCmd.builder().appId(wechatMiniProgramQuery.getAppId()).appSecret(wechatMiniProgramQuery.getSecret()).code(wechatMiniProgramQuery.getJsCode()).build();
        SingleResponse<LoginInfoCO> response = wechatFacade.getLoginInfo(loginCmd);
        if (!response.isSuccess()) {
            throw new SysException(response.getErrCode(), response.getErrMessage());
        }
        return response.getData();
    }
}
