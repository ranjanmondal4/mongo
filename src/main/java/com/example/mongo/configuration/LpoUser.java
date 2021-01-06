package com.example.mongo.configuration;

import com.example.mongo.domain.redis.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor(staticName = "of")
public class LpoUser implements UserDetails {
    @ApiModelProperty(hidden = true)
    private UserInfo userInfo;

    @ApiModelProperty(hidden = true)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userInfo.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @ApiModelProperty(hidden = true)
    @Override
    public String getPassword() {
        return userInfo.getToken();
    }

    @ApiModelProperty(hidden = true)
    @Override
    public String getUsername() {
        return userInfo.getUserId();
    }

    @ApiModelProperty(hidden = true)
    public String getUserEmail() {
        return "Email - Dummy";
    }

    @ApiModelProperty(hidden = true)
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public boolean isEnabled() {
        return false;
    }
}
