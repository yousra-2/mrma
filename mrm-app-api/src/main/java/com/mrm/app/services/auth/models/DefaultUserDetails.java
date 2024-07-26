package com.mrm.app.services.auth.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUserDetails implements UserDetails {

    private static final String SEPARATOR = ",";

    private final String username;
    private final String password;
    private final boolean active;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final List<GrantedAuthority> authorities;

    private DefaultUserDetails(String username,
                               String password,
                               boolean active,
                               String firstName,
                               String lastName,
                               String email,
                               List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.authorities = authorities;
    }

    public static DefaultUserDetails from(String username,
                                          String password,
                                          boolean active,
                                          String firstName,
                                          String lastName,
                                          String email,
                                          String authorities) {
        List<GrantedAuthority> grantedAuthorities;
        if (authorities == null) {
            grantedAuthorities = Collections.emptyList();
        } else {
            grantedAuthorities = Arrays.stream(authorities.split(SEPARATOR))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        return new DefaultUserDetails(username, password, active, firstName, lastName, email, grantedAuthorities);
    }

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
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
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
        return active;
    }
}
