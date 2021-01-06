package com.example.mongo.domain.documents;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@org.springframework.data.mongodb.core.mapping.Document
@Data
@NoArgsConstructor
public class Document {
    @NonNull
    private String name;
    @NonNull
    private String type;
    private boolean deleted = false;

    public static Document of(String name, String type){
        Document document = new Document();
        document.name = name;
        document.type = type;
        return document;
    }
}
