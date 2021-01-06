package com.example.mongo.repo;

import com.example.mongo.domain.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContactRepo extends MongoRepository<Contact, String> {
    List<Contact> findByIdInAndUser_Id(List<String> contactIds, String userId);
}
