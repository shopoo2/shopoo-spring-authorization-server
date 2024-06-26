package com.szmengran.authorization.infrastructure.admin.database;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szmengran.authorization.domain.admin.entity.Oauth2Menu;
import com.szmengran.authorization.domain.admin.valueobject.Oauth2MenuExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description 菜单数据库操作接口
 * @Date 2020/10/29 4:51 下午
 * @Author <a href="mailto:android_li@sina.cn">Joe</a>
 **/
@Mapper
public interface MenuMapper extends BaseMapper<Oauth2Menu> {

    String querySql = "select a.*, e.user_id from oauth2_menu A, oauth2_role B, oauth2_role_menu_r C, oauth_user_role_r e where A.menu_id = C.menu_id and B.role_id = C.role_id and b.role_id = e.role_id";
    String wrapperSql = "SELECT * from ( " + querySql + " ) q ${ew.customSqlSegment}";

    @Select(wrapperSql)
    List<Oauth2MenuExt> findMenuByConditions(@Param("ew") Wrapper queryWrapper);

    /**
     * @Description 根据角色查找角色的菜单
     *
     * @Param [roleid]
     * @Return java.util.List<com.suntak.system.infrastructure.db.entity.OauthMenu>
     * @Date 2:48 下午 2020/12/3
     * @Author <a href="mailto:android_li@sina.cn">Joe</a>
     */
    @Select("select a.* from oauth2_menu a, oauth2_role_menu_r b where a.menu_id = b.menu_id and a.status=1 and b.role_id = #{roleId}")
    List<Oauth2MenuExt> findByRoleId(Long roleId);

    /**
     * @Description: 查找所有的角色
     * @Author: limy66
     * @Date:   2021/5/17 9:39
     * @Param:  [roleId]
     * @Return: java.util.List<com.bngrp.system.infrastructure.db.vo.MenuV>
     */
    @Select("select a.*,b.role_id from oauth2_menu a left join oauth2_role_menu_r b on a.menu_id = b.menu_id and a.status=1 and b.role_id = #{roleId} order by level, sort desc")
    List<Oauth2MenuExt> findAllMenu(Long roleId);
}
