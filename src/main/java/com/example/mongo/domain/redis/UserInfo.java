package com.example.mongo.domain.redis;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
@RedisHash(timeToLive = 60*60*24)
public class UserInfo {
    private static final long serialVersionUID = -2601175617857390837L;
    @Id
    private String userId;
    @Indexed
    private String token;
    private List<String> roles;
}
