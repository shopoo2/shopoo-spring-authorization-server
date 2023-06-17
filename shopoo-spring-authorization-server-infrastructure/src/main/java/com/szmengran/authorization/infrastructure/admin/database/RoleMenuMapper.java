package com.szmengran.authorization.infrastructure.admin.database;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szmengran.authorization.domain.admin.entity.Oauth2RoleMenuR;
import com.szmengran.authorization.domain.admin.valueobject.Oauth2MenuExt;
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
public interface RoleMenuMapper extends BaseMapper<Oauth2RoleMenuR> {

    String querySql = "select b.name, a.url from oauth2.oauth2_menu a, oauth2.oauth2_role b, oauth2.oauth2_role_menu_r c where a.menu_id = c.menu_id and b.role_id = c.role_id and a.status=1 and b.status=1";
    String wrapperSql = "SELECT * from ( " + querySql + " ) q ${ew.customSqlSegment}";

    @Select(wrapperSql)
    List<Oauth2MenuExt> findRoleMenus(@Param("ew") Wrapper queryWrapper);
}
