package com.example.mongo.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    private static final long serialVersionUID = -8524087869422730778L;
    private UserDetails user;

    public static UserAuthentication of(UserDetails user) {
        UserAuthentication authentication = new UserAuthentication();
        authentication.user = user;
        return authentication;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return user.getUsername();
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public String toString() {
        return user.toString();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }



}
