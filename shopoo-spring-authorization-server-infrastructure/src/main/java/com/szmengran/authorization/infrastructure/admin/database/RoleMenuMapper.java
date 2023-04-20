package com.szmengran.authorization.infrastructure.admin.database;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szmengran.authorization.domain.admin.entity.OauthRoleMenuR;
import com.szmengran.authorization.domain.admin.valueobject.OauthMenuExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description 角色菜单数据操作
 * @Date 2020/12/3 11:07 上午
 * @Author <a href="mailto:android_li@sina.cn">Joe</a>
 **/
@Mapper
public interface RoleMenuMapper extends BaseMapper<OauthRoleMenuR> {

    String querySql = "select b.name, a.url from oauth.oauth_menu a, oauth.oauth_role b, oauth.oauth_role_menu_r c where a.menu_id = c.menu_id and b.role_id = c.role_id and a.status=1 and b.status=1";
    String wrapperSql = "SELECT * from ( " + querySql + " ) q ${ew.customSqlSegment}";

    @Select(wrapperSql)
    List<OauthMenuExt> findRoleMenus(@Param("ew") Wrapper queryWrapper);
}
