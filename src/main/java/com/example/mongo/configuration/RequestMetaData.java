package com.example.mongo.configuration;

import com.example.mongo.util.AppConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;

@Component
@RequestScope
@Slf4j
@Getter
public class RequestMetaData {
    private String token;
    public RequestMetaData(HttpServletRequest request){
        token = request.getHeader(AppConstants.AUTHORIZATION);
    }
}
