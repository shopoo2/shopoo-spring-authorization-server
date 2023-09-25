package com.szmengran.authorization.infrastructure.oauth2.client;

import com.szmengran.authorization.dto.TokenCO;
import com.szmengran.authorization.dto.cqe.MiniProgramTokenQueryCmd;
import com.szmengran.authorization.dto.cqe.TokenQueryCmd;
import com.szmengran.authorization.infrastructure.oauth2.utils.Constants;
import feign.QueryMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${spring.application.name}")
public interface Oauth2Client {

	@PostMapping(value = Constants.OAUTH2_URL, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	TokenCO getToken(@RequestHeader("Authorization") String authorization, @QueryMap TokenQueryCmd tokenQueryCmd);
	
	@PostMapping(value = Constants.OAUTH2_URL, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	TokenCO getMiniProgramToken(@RequestHeader("Authorization") String authorization, @QueryMap MiniProgramTokenQueryCmd miniProgramTokenQueryCmd);
}
