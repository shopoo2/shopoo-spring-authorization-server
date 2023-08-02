package com.szmengran.authorization.domain.admin.repository;

import com.szmengran.authorization.domain.admin.entity.Oauth2Wechat;

public interface WechatRepository {

	/**
	 * 添加用户
	 * @param oauth2Wechat
	 * @Date: 2023/6/17 16:38
	 * @Author: <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
	 */
	void add(Oauth2Wechat oauth2Wechat);
	
	/**
	 * 更新用户
	 * @param oauth2Wechat
	 * @Return java.lang.Integer
	 * @Date: 2023/6/17 16:41
	 * @Author: <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
	 */ 
	Integer update(Oauth2Wechat oauth2Wechat);
}
