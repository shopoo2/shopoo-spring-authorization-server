package com.szmengran.authorization.infrastructure.admin.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.szmengran.authorization.domain.admin.entity.Oauth2UserRoleR;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 用户角色数据操作
 * @Date 2020/12/3 11:07 上午
 * @Author <a href="mailto:android_li@sina.cn">Joe</a>
 **/
@Mapper
public interface UserRoleMapper extends BaseMapper<Oauth2UserRoleR> {

}
