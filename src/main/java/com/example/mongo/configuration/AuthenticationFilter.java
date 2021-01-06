package com.example.mongo.configuration;

import com.example.mongo.domain.redis.UserInfo;
import com.example.mongo.repo.UserInfoRepo;
import com.example.mongo.repo.UserRepo;
import com.example.mongo.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class AuthenticationFilter extends GenericFilterBean {

    private UserInfoRepo userInfoRepo;

    public static AuthenticationFilter of(UserInfoRepo userInfoRepo){
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.userInfoRepo = userInfoRepo;
        return authenticationFilter;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader(AppConstants.AUTHORIZATION);
//        log.info("In Authentication filter, Token {}", token);
        if(!StringUtils.isEmpty(token)){
            UserInfo userInfo = userInfoRepo.findByToken(token);
            UserDetails lpoUser = LpoUser.of(userInfo);
            if(!Objects.isNull(userInfo)){
                SecurityContextHolder.getContext().setAuthentication(UserAuthentication.of(lpoUser));
            }
        }
        chain.doFilter(request, response);
    }
}
