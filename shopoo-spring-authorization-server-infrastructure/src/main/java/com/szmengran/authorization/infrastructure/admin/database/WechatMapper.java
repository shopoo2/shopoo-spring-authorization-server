package com.szmengran.authorization.infrastructure.admin.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szmengran.authorization.domain.admin.entity.Oauth2Wechat;
import com.szmengran.authorization.domain.admin.valueobject.UserDetailsExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WechatMapper extends BaseMapper<Oauth2Wechat> {

	/**
	 * 
	 * @description 根据openid查询用户信息
	 * @param openid
	 * @return
	 * @date Mar 6, 2020 2:45:41 PM
	 * @author <a href="mailto:android_li@sina.cn">Joe</a>
	 */
	@Select("select * from oauth2_wechat where openid=#{openid}")
	UserDetailsExt findByOpenid(String openid);

}
