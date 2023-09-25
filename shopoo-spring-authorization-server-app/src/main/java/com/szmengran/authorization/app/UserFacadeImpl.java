package com.szmengran.authorization.app;

import java.util.Base64;

import com.szmengran.authorization.api.UserFacade;
import com.szmengran.authorization.domain.admin.UserDomainService;
import com.szmengran.authorization.dto.TokenCO;
import com.szmengran.authorization.dto.cqe.MiniProgramTokenQueryCmd;
import com.szmengran.authorization.dto.cqe.TokenQueryCmd;
import com.szmengran.authorization.dto.cqe.UserRegisterCmd;
import com.szmengran.authorization.dto.cqe.UserUpdateCmd;
import com.szmengran.authorization.infrastructure.oauth2.client.Oauth2Client;
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

	@Resource
	private Oauth2Client oauth2Client;

	@Override
	public TokenCO login(final TokenQueryCmd tokenQueryCmd) {
		String authorization = "Basic " + Base64.getEncoder().encodeToString((tokenQueryCmd.getClientId() + ":" + tokenQueryCmd.getClientSecret()).getBytes());
		tokenQueryCmd.setClientSecret(null);
		tokenQueryCmd.setClientId(null);
		return oauth2Client.getToken(authorization, tokenQueryCmd);
	}

	@Override
	public TokenCO miniProgramLogin(MiniProgramTokenQueryCmd miniProgramTokenQueryCmd) {
		String authorization = "Basic " + Base64.getEncoder().encodeToString((miniProgramTokenQueryCmd.getClientId() + ":" + miniProgramTokenQueryCmd.getClientSecret()).getBytes());
		miniProgramTokenQueryCmd.setClientSecret(null);
		miniProgramTokenQueryCmd.setClientId(null);
		return oauth2Client.getMiniProgramToken(authorization, miniProgramTokenQueryCmd);
	}


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
