package com.example.mongo.repo;

import com.example.mongo.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByPrimaryEmail_EmailAndPrimaryEmail_Verified(String email, boolean verified);
    Optional<User> findByUserName(String userName);
}
