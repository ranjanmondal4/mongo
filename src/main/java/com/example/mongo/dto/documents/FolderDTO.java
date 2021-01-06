package com.example.mongo.dto.documents;

import com.example.mongo.domain.documents.AllowedAction;
import com.example.mongo.domain.documents.Document;
import com.example.mongo.domain.documents.Folder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class FolderDTO {
    private String id;
    private String name;
    private Folder.BelongsTo belongsTo;
    private AllowedAction allowedAction;
    private List<Document> documents;
    private List<ChildFolderDTO> childFolders;

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class ChildFolderDTO {
        private String id;
        private String name;
        private Folder.BelongsTo belongsTo;
        private int documentCount;
    }
}
