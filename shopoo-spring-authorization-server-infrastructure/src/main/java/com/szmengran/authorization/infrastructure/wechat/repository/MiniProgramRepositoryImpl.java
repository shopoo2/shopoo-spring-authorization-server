package com.szmengran.authorization.infrastructure.wechat.repository;

import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.shopoo.common.wechat.api.WechatFacade;
import com.shopoo.common.wechat.dto.clientobject.LoginInfoCO;
import com.shopoo.common.wechat.dto.cqe.LoginCmd;
import com.szmengran.authorization.domain.wechat.repository.MiniProgramRepository;
import com.szmengran.authorization.dto.cqe.WechatMiniProgramQuery;
import com.szmengran.authorization.infrastructure.wechat.client.MiniProgramClient;
import com.szmengran.cola.dto.SingleResponse;
import com.szmengran.cola.exception.Assert;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.data.redis.core.RedisTemplate;
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

    @DubboReference
    private WechatFacade wechatFacade;

    @Resource
    private RedisTemplate redisTemplate;
    
    @Override
    public LoginInfoCO login(final WechatMiniProgramQuery wechatMiniProgramQuery) {
        LoginCmd loginCmd = LoginCmd.builder().appId(wechatMiniProgramQuery.getAppId()).appSecret(wechatMiniProgramQuery.getSecret()).code(wechatMiniProgramQuery.getJsCode()).build();
        log.info("wechat login request：{}", loginCmd);
        String json = miniProgramClient.getLoginInfo(loginCmd.getAppId(), loginCmd.getAppSecret(), loginCmd.getCode());
        log.info("wechat login response：{}", json);
        LoginInfoCO loginInfoCO = new Gson().fromJson(json, LoginInfoCO.class);
        redisTemplate.opsForValue().set(wechatMiniProgramQuery.getJsCode(), loginInfoCO, 60, TimeUnit.SECONDS);
        return loginInfoCO;
    }
}
