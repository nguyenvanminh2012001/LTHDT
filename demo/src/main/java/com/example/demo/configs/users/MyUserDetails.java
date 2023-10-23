package com.example.demo.configs.users;


import com.example.demo.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Get user principal
 */
public class MyUserDetails implements UserDetails {
    private final UserEntity userEntity;

    public MyUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


    public Long getUserId() {
        return userEntity.getId();
    }

    public Long getUserType() {
        return Objects.equals(userEntity.getUserType(), "ADMIN") ? 0L : 1L;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getUserType()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserName();
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
