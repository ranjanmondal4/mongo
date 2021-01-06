package com.example.mongo.domain.documents;

import com.example.mongo.domain.User;
import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Collections;
import java.util.List;

@org.springframework.data.mongodb.core.mapping.Document
@Data
@RequiredArgsConstructor(staticName = "of")
public class Folder {
    @Id
    private String id;
    @NonNull
    @DBRef
    private User user;
    @NonNull
    private String name;
    @NonNull
    private BelongsTo belongsTo;
    public enum BelongsTo {
        CLIENT, SPOUSE, JOINT, CHILD;
    }
//    @NonNull
    private boolean deleted = false;
    @DBRef
    private Folder parentFolder;
    @NonNull
    private AllowedAction allowedAction;
    private List<Document> documents = Collections.emptyList();
}
