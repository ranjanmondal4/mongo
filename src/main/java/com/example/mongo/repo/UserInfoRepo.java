package com.example.mongo.repo;

import com.example.mongo.domain.redis.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepo extends CrudRepository<UserInfo, String> {
    UserInfo findByToken(String token);
}
