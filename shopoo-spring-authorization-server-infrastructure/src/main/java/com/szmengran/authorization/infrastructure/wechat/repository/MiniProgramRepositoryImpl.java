package com.szmengran.authorization.infrastructure.wechat.repository;

import com.nimbusds.jose.shaded.gson.Gson;
import com.szmengran.authorization.domain.wechat.repository.MiniProgramRepository;
import com.szmengran.authorization.dto.WechatMiniProgramCO;
import com.szmengran.authorization.dto.cqe.WechatMiniProgramQuery;
import com.szmengran.authorization.infrastructure.wechat.client.MiniProgramClient;
import com.szmengran.cola.exception.Assert;
import com.szmengran.cola.exception.SysException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 20:40
 * @Version 1.0
 */
@Repository
public class MiniProgramRepositoryImpl implements MiniProgramRepository {
    
    @Resource
    private MiniProgramClient miniProgramClient;
    
    @Override
    public WechatMiniProgramCO jscode2session(final WechatMiniProgramQuery wechatMiniProgramQuery) {
        String json = miniProgramClient.jscode2session(wechatMiniProgramQuery.getAppid(), wechatMiniProgramQuery.getSecret(), wechatMiniProgramQuery.getJsCode());
        WechatMiniProgramCO wechatMiniProgramCO = new Gson().fromJson(json, WechatMiniProgramCO.class);
        if (wechatMiniProgramCO.getErrcode() != 0) {
            throw new SysException(String.valueOf(wechatMiniProgramCO.getErrcode()), wechatMiniProgramCO.getErrmsg());
        }
        return wechatMiniProgramCO;
    }
}
