package com.szmengran.authorization.api;

import com.szmengran.authorization.dto.cqe.UserRegisterCmd;
import com.szmengran.authorization.dto.cqe.UserUpdateCmd;
import com.szmengran.cola.dto.Response;

public interface UserFacade {

	/**
	 * 用户注册
	 * @param userRegisterCmd
	 * @Return com.szmengran.cola.dto.Response
	 * @Date: 2023/6/17 11:41
	 * @Author: <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
	 */ 
	Response register(UserRegisterCmd userRegisterCmd);

	/**
	 * 用户信息更新
	 * @param userUpdateCmd
	 * @Return com.szmengran.cola.dto.Response
	 * @Date: 2023/6/17 16:25
	 * @Author: <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
	 */ 
	Response update(UserUpdateCmd userUpdateCmd);
}
