package com.szmengran.authorization.api;

import com.szmengran.authorization.dto.TokenCO;
import com.szmengran.authorization.dto.cqe.MiniProgramTokenQueryCmd;
import com.szmengran.authorization.dto.cqe.TokenQueryCmd;
import com.szmengran.authorization.dto.cqe.UserRegisterCmd;
import com.szmengran.authorization.dto.cqe.UserUpdateCmd;
import com.szmengran.cola.dto.Response;

public interface UserFacade {

	/**
	 * @description: 用户登录
	 * @param tokenQueryCmd
	 * @return: com.szmengran.chatgpt.dto.user.TokenCO
	 * @author MaoYuan.Li
	 * @date: 2023/7/24 21:04
	 */
	TokenCO login(TokenQueryCmd tokenQueryCmd);

	/**
	 * @description: 小程序登录
	 * @param tokenQueryCmd
	 * @return: com.szmengran.chatgpt.dto.user.TokenCO
	 * @author MaoYuan.Li
	 * @date: 2023/8/1 19:21
	 */
	TokenCO miniProgramLogin(MiniProgramTokenQueryCmd tokenQueryCmd);

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
