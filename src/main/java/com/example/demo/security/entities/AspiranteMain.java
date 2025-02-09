package com.example.demo.security.entities;

import com.example.demo.security.enums.RoleName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AspiranteMain implements UserDetails {
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public AspiranteMain(String email, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.email = email;
        this.grantedAuthorities = grantedAuthorities;
    }

    public static AspiranteMain build(Aspirante aspirante) {
        List<GrantedAuthority> grantedAuthorities =
                Collections.singletonList(new SimpleGrantedAuthority(RoleName.ROLE_ASPIRANTE.name()));
        return new AspiranteMain(aspirante.getEmail(), grantedAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

