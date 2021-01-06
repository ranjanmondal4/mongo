package com.example.mongo.repo;

import com.example.mongo.domain.documents.Folder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepo extends MongoRepository<Folder, String> {

    Optional<Folder> findByNameAndUser_IdAndParentFolder_IdAndDeletedFalse(String name, String userId, String parentFolderId);
    Optional<Folder> findByNameAndUser_IdAndParentFolderIsNullAndDeletedFalse(String name, String userId);

    List<Folder> findAllByUser_IdAndParentFolderIsNullAndDeletedFalse(String userId);
    Optional<Folder> findByIdAndUser_IdAndDeletedFalse(String id, String userId);
    List<Folder> findAllByUser_IdAndParentFolder_IdAndDeletedFalse(String userId, String parentId);
}
