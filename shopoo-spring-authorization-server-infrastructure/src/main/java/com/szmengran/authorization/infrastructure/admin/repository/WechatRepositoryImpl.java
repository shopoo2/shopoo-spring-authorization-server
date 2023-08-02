package com.szmengran.authorization.infrastructure.admin.repository;

import com.szmengran.authorization.domain.admin.entity.Oauth2Wechat;
import com.szmengran.authorization.domain.admin.repository.WechatRepository;
import com.szmengran.authorization.domain.admin.valueobject.Oauth2RoleExt;
import com.szmengran.authorization.domain.admin.valueobject.UserDetailsExt;
import com.szmengran.authorization.infrastructure.admin.database.RoleMapper;
import com.szmengran.authorization.infrastructure.admin.database.WechatMapper;
import com.szmengran.cola.exception.BizException;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用户仓库
 * @Date 2023/8/2 1:54 下午
 * @Author <a href="mailto:android_li@sina.cn">Joe</a>
 **/
@Repository("wechatDetailsService")
public class WechatRepositoryImpl implements UserDetailsService, WechatRepository {

    @Resource
    private WechatMapper wechatMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String openid) throws UsernameNotFoundException {
        UserDetailsExt user = wechatMapper.findByOpenid(openid);
        if (user == null) {
            return null;
        }
        if (user.getStatus() != 1) {
            throw new BizException("用户已被禁用，请联系平台管理员");
        }
        user.setAuthorities(new ArrayList<>());
        return user;
    }

    @Override
    public void add(Oauth2Wechat oauth2Wechat) {
        wechatMapper.insert(oauth2Wechat);
    }
    
    @Override
    public Integer update(Oauth2Wechat oauth2Wechat) {
        return wechatMapper.updateById(oauth2Wechat);
    }
}
