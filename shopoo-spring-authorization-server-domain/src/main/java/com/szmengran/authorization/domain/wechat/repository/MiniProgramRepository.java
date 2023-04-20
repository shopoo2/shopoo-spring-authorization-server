package com.szmengran.authorization.domain.wechat.repository;

import com.szmengran.authorization.dto.WechatMiniProgramCO;
import com.szmengran.authorization.dto.cqe.WechatMiniProgramQuery;

/**
 * @Author MaoYuan.Li
 * @Date 2023/4/19 19:48
 * @Version 1.0
 */
public interface MiniProgramRepository {
    
    /** 
     * @description: 获取微信小程序登录信息
     * @param wechatMiniProgramQuery 
     * @return: com.szmengran.authorization.dto.WechatMiniProgramCO 
     * @author MaoYuan.Li
     * @date: 2023/4/19 19:52
     */
    WechatMiniProgramCO jscode2session(WechatMiniProgramQuery wechatMiniProgramQuery);
}
