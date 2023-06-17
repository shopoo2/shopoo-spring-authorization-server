package com.szmengran.authorization.domain.admin;

import com.szmengran.authorization.domain.admin.converter.Converter;
import com.szmengran.authorization.domain.admin.entity.Oauth2User;
import com.szmengran.authorization.domain.admin.repository.UserRepository;
import com.szmengran.authorization.domain.utils.IDUtils;
import com.szmengran.authorization.dto.cqe.UserRegisterCmd;
import com.szmengran.authorization.dto.cqe.UserUpdateCmd;
import jakarta.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @Author <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
 * @Date 2023/6/17 16:29
 */
@Service
public class UserDomainService {

	@Resource
	private UserRepository userRepository;

	@Resource
	private PasswordEncoder passwordEncoder;


	public void register(UserRegisterCmd userRegisterCmd) {
		String userId = IDUtils.getSnowId("10");
		Oauth2User oauth2User = Converter.INSTANCE.toOauth2User(userRegisterCmd, userId);
		oauth2User.setPassword(passwordEncoder.encode(oauth2User.getPassword()));
		userRepository.add(oauth2User);
	}

	public void update(UserUpdateCmd userUpdateCmd) {
		Oauth2User oauth2User = Converter.INSTANCE.toOauth2User(userUpdateCmd);
		oauth2User.setPassword(null);
		userRepository.update(oauth2User);
	}
}
