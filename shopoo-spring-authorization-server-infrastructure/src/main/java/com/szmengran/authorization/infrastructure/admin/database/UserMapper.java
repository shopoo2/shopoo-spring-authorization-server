package com.szmengran.authorization.infrastructure.admin.database;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szmengran.authorization.domain.admin.entity.Oauth2User;
import com.szmengran.authorization.domain.admin.valueobject.Oauth2UserExt;
import com.szmengran.authorization.domain.admin.valueobject.UserDetailsExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<Oauth2User> {

	String querySql = "select a.*, (select GROUP_CONCAT(c.name) from oauth2.oauth2_user_role_r b left join oauth2.oauth2_role c on b.role_id = c.role_id where a.user_id = b.user_id ) roleName from oauth2.oauth2_user a";
	String wrapperSql = "SELECT * from ( " + querySql + " ) q ${ew.customSqlSegment}";

	String querySql1 = "select a.*, c.name roleName from oauth2.oauth2kl_user a, oauth2.oauth2_user_role_r b, oauth2.oauth2_role c where b.role_id = c.role_id and a.user_id = b.user_id";
	String wrapperSql1 = "SELECT * from ( " + querySql1 + " ) q ${ew.customSqlSegment}";

	@Select(wrapperSql)
	List<Oauth2UserExt> findUsers(@Param("ew") Wrapper queryWrapper);

	@Select(wrapperSql1)
	List<Oauth2UserExt> findByUserInfo(@Param("ew") Wrapper queryWrapper);

	/**
	 * 
	 * @description 根据用户名称查询用户信息
	 * @param username
	 * @return
	 * @date Mar 6, 2020 2:45:41 PM
	 * @author <a href="mailto:android_li@sina.cn">Joe</a>
	 */
	@Select("select * from oauth2_user where username=#{username}")
	UserDetailsExt findByUsername(String username);

}
