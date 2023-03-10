package com.recanto.recanto.security;

import com.recanto.recanto.enums.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {
    private final Integer id;
    private final String email;
    private final String name;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserSS(Integer id, String email, String name, String password, Set<Profile> profiles) {
        super();
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = profiles.stream().map(
                profile -> new SimpleGrantedAuthority(
                        profile.getDescription())).collect(Collectors.toSet());
    }

    public Integer getId() {
        return id;
    }
    public String getName() {return name;}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
