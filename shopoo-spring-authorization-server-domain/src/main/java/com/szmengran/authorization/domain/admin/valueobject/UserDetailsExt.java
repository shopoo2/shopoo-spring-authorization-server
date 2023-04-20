package com.szmengran.authorization.domain.admin.valueobject;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description:
 * @Auther: limy66
 * @Date: 2021/08/31/16:10
 */
@Data
public class UserDetailsExt implements UserDetails, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2638346706733390711L;
    private String userId;
    private String username;
    private String password;
    private Short status;
    private Collection<? extends GrantedAuthority> authorities;

    public void setAuthorities(List<OauthRoleExt> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (OauthRoleExt role : roles) {
            String name = role.getName().toUpperCase();
            authorities.add(new SimpleGrantedAuthority(name));
        }
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
