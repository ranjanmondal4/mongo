package com.example.mongo.repo;

import com.example.mongo.domain.EmergencyCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmergencyCardRepo extends MongoRepository<EmergencyCard, String> {
    List<EmergencyCard> findByUser_Id(String userId);
}
