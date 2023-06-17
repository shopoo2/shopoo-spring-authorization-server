package com.szmengran.authorization.infrastructure.admin.repository;

import com.szmengran.authorization.domain.admin.entity.Oauth2User;
import com.szmengran.authorization.domain.admin.repository.UserRepository;
import com.szmengran.authorization.domain.admin.valueobject.Oauth2RoleExt;
import com.szmengran.authorization.domain.admin.valueobject.UserDetailsExt;
import com.szmengran.authorization.infrastructure.admin.database.RoleMapper;
import com.szmengran.authorization.infrastructure.admin.database.UserMapper;
import com.szmengran.cola.exception.BizException;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @Description 用户仓库
 * @Date 2020/5/24 1:54 下午
 * @Author <a href="mailto:android_li@sina.cn">Joe</a>
 **/
@Repository
public class UserRepositoryImpl implements UserDetailsService, UserRepository {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsExt user = userMapper.findByUsername(username);
        Assert.notNull(user, String.format("user (%s) do not exist!", username));
        if (user.getStatus() != 1) {
            throw new BizException("用户已被禁用，请联系平台管理员");
        }
        List<Oauth2RoleExt> roles = roleMapper.findByUserId(user.getUserId());
        user.setAuthorities(roles);
        return user;
    }

    @Override
    public void add(Oauth2User oauth2User) {
        userMapper.insert(oauth2User);
    }

    @Override
    public Integer update(Oauth2User oauth2User) {
        return userMapper.updateById(oauth2User);
    }
}
