package com.szmengran.authorization.infrastructure.wechat.repository;

import com.google.gson.Gson;
import com.shopoo.wechat.dto.clientobject.LoginInfoCO;
import com.shopoo.wechat.dto.cqe.LoginCmd;
import com.szmengran.authorization.domain.wechat.repository.MiniProgramRepository;
import com.szmengran.authorization.dto.cqe.WechatMiniProgramQuery;
import com.szmengran.authorization.infrastructure.wechat.client.MiniProgramClient;
import com.szmengran.cola.exception.SysException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 20:40
 * @Version 1.0
 */
@Slf4j
@Repository
public class MiniProgramRepositoryImpl implements MiniProgramRepository {
    
    @Resource
    private MiniProgramClient miniProgramClient;
    
    @Override
    public LoginInfoCO login(final WechatMiniProgramQuery wechatMiniProgramQuery) {
        LoginCmd loginCmd = LoginCmd.builder().appId(wechatMiniProgramQuery.getAppId()).appSecret(wechatMiniProgramQuery.getSecret()).code(wechatMiniProgramQuery.getJsCode()).build();
        log.info("wechat login request：{}", loginCmd);
        String json = miniProgramClient.getLoginInfo(loginCmd.getAppId(), loginCmd.getAppSecret(), loginCmd.getCode());
        log.info("wechat login response：{}", json);
        LoginInfoCO loginInfoCO = new Gson().fromJson(json, LoginInfoCO.class);
        if (null != loginInfoCO && loginInfoCO.getErrcode() != null) {
            throw new SysException(loginInfoCO.getErrcode().toString(), loginInfoCO.getErrmsg());
        }
        
        return loginInfoCO;
    }
}
