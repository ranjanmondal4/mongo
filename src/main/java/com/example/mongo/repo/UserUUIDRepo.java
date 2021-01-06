package com.example.mongo.repo;

import com.example.mongo.domain.redis.UserUUID;
import org.springframework.data.repository.CrudRepository;

public interface UserUUIDRepo extends CrudRepository<UserUUID, String> {
}
