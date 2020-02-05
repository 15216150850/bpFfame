package com.bp.act.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author 钟欣凯
 * spring security当前登录对象
 */
@Getter
@Setter
public class LoginUser extends SysUser implements UserDetails {

    private static final long serialVersionUID = 1753977564987556640L;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (!CollectionUtils.isEmpty(this.getSysRoles())) {
            this.getSysRoles().forEach(role -> {
                if (role.startsWith("ROLE_")) {
                    collection.add(new SimpleGrantedAuthority(role));
                } else {
                    collection.add(new SimpleGrantedAuthority("ROLE_" + role));
                }
            });
        }

        if (!CollectionUtils.isEmpty(this.getPermissions())) {
//			this.getPermissions().forEach(per -> collection.add(new SimpleGrantedAuthority(per)));
            for (String per :
                    this.getPermissions()) {
                if (!"".equals(per)) {
                    collection.add(new SimpleGrantedAuthority(per));
                }
            }
        }

        return collection;
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
        return getEnabled();
    }
}
