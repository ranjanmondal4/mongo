package com.example.mongo.domain.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor(staticName = "of")
@RedisHash(timeToLive = 3600)
public class UserUUID {
    @Id
    private String uuid;
    private String userId;
}
