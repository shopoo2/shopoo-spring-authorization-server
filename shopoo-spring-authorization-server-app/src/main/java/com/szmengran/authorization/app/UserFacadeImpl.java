package com.szmengran.authorization.app;

import com.szmengran.authorization.api.UserFacade;
import com.szmengran.authorization.domain.admin.UserDomainService;
import com.szmengran.authorization.dto.cqe.UserRegisterCmd;
import com.szmengran.authorization.dto.cqe.UserUpdateCmd;
import com.szmengran.cola.dto.Response;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 *
 * @Author <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
 * @Date 2023/6/17 16:25
 */
@DubboService
public class UserFacadeImpl implements UserFacade {

	@Resource
	private UserDomainService userDomainService;

	@Override
	public Response register(UserRegisterCmd userRegisterCmd) {
		userDomainService.register(userRegisterCmd);
		return Response.buildSuccess();
	}

	@Override
	public Response update(UserUpdateCmd userUpdateCmd) {
		userDomainService.update(userUpdateCmd);
		return Response.buildSuccess();
	}
}
