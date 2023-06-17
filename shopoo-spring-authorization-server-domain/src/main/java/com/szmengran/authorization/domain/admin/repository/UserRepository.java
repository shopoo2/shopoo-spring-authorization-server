package com.szmengran.authorization.domain.admin.repository;

import com.szmengran.authorization.domain.admin.entity.Oauth2User;

public interface UserRepository {

	/**
	 * 添加用户
	 * @param oauth2User
	 * @Date: 2023/6/17 16:38
	 * @Author: <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
	 */
	void add(Oauth2User oauth2User);
	
	/**
	 * 更新用户
	 * @param oauth2User
	 * @Return java.lang.Integer
	 * @Date: 2023/6/17 16:41
	 * @Author: <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
	 */ 
	Integer update(Oauth2User oauth2User);
}
