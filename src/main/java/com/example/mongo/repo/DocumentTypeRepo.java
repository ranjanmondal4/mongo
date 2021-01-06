package com.example.mongo.repo;

import com.example.mongo.domain.documents.DocumentType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentTypeRepo extends MongoRepository<DocumentType, String> {
    Optional<DocumentType> findByIdAndUser_Id(String id, String userId);
    List<DocumentType> findByUser_IdAndDeleted(String user, boolean deleted);
}
