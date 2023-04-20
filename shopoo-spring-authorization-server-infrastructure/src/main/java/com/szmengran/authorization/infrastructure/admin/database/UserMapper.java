package com.szmengran.authorization.infrastructure.admin.database;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szmengran.authorization.domain.admin.entity.OauthUser;
import com.szmengran.authorization.domain.admin.valueobject.OauthUserExt;
import com.szmengran.authorization.domain.admin.valueobject.UserDetailsExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<OauthUser> {

	String querySql = "select a.*, (select GROUP_CONCAT(c.name) from oauth.oauth_user_role_r b left join oauth.oauth_role c on b.role_id = c.role_id where a.user_id = b.user_id ) roleName from oauth.oauth_user a";
	String wrapperSql = "SELECT * from ( " + querySql + " ) q ${ew.customSqlSegment}";

	String querySql1 = "select a.*, c.name roleName from oauth.oauth_user a, oauth.oauth_user_role_r b, oauth.oauth_role c where b.role_id = c.role_id and a.user_id = b.user_id";
	String wrapperSql1 = "SELECT * from ( " + querySql1 + " ) q ${ew.customSqlSegment}";

	@Select(wrapperSql)
	List<OauthUserExt> findUsers(@Param("ew") Wrapper queryWrapper);

	@Select(wrapperSql1)
	List<OauthUserExt> findByUserInfo(@Param("ew") Wrapper queryWrapper);

	/**
	 * 
	 * @description 根据用户名称查询用户信息
	 * @param username
	 * @return
	 * @date Mar 6, 2020 2:45:41 PM
	 * @author <a href="mailto:android_li@sina.cn">Joe</a>
	 */
	@Select("select * from oauth_user where username=#{username}")
	UserDetailsExt findByUsername(String username);

}
