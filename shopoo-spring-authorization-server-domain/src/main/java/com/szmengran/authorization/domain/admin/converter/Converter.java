package com.szmengran.authorization.domain.admin.converter;

import com.szmengran.authorization.domain.admin.entity.Oauth2User;
import com.szmengran.authorization.domain.admin.valueobject.UserDetailsExt;
import com.szmengran.authorization.dto.UserDetailsCO;
import com.szmengran.authorization.dto.cqe.UserRegisterCmd;
import com.szmengran.authorization.dto.cqe.UserUpdateCmd;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Converter {

	Converter INSTANCE = Mappers.getMapper(Converter.class);

	Oauth2User toOauth2User(UserRegisterCmd userRegisterCmd, String userId);
	Oauth2User toOauth2User(UserUpdateCmd userUpdateCmd);
	UserDetailsCO toUserDetailsCO(UserDetailsExt userDetailsExt);
}
