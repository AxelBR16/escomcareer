package com.example.demo.security.entities;

import com.example.demo.security.enums.RoleName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EgresadoMain implements UserDetails {
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public EgresadoMain(String email, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.email = email;
        this.grantedAuthorities = grantedAuthorities;
    }

    public static EgresadoMain build(Egresado egresado) {
        List<GrantedAuthority> grantedAuthorities =
                Collections.singletonList(new SimpleGrantedAuthority(RoleName.ROLE_EGRESADO.name()));
        return new EgresadoMain(egresado.getEmail(), grantedAuthorities);
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
