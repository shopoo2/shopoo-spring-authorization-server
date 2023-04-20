package com.szmengran.authorization.infrastructure.admin.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szmengran.authorization.domain.admin.entity.OauthRole;
import com.szmengran.authorization.domain.admin.valueobject.OauthRoleExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description 角色持久化操作
 * @Date 2020/5/24 1:28 下午
 * @Author <a href="mailto:android_li@sina.cn">Joe</a>
 **/
@Mapper
public interface RoleMapper extends BaseMapper<OauthRole> {
    
    /**
     * @Description 根据userId获取角色
     *
     * @Param []
     * @Return java.util.List<com.suntak.security.oauth.domain.entity.OauthRole>
     * @Date 1:31 下午 2020/5/24
     * @Author <a href="mailto:android_li@sina.cn">Joe</a>
     */
    @Select("select a.* from oauth_role a, oauth_user_role_r b where a.role_id = b.role_id and b.user_id = #{userId}")
    List<OauthRoleExt> findByUserId(String userId);

    /**
     * @Description: 获取所有的角色
     * @Author: limy66
     * @Date:   2021/5/17 8:18
     * @Param:  [userId]
     * @Return: java.util.List<com.bngrp.system.domain.vo.RoleV>
     */
    @Select("select a.*,b.user_id from oauth_role a left join oauth_user_role_r b on a.role_id = b.role_id and b.user_id = #{userId}")
    List<OauthRoleExt> findAllRole(String userId);

}
