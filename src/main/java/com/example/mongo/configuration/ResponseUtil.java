package com.example.mongo.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseUtil {

    public static ResponseEntity<Object> generate(boolean success, Object data, String message){
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("message", message);
        return new ResponseEntity<>(
                response,
                success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
