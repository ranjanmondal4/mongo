package com.example.mongo.dto.documents;

import com.example.mongo.domain.documents.DocumentType;
import lombok.Data;

import java.util.List;

@Data
public class UpdateContactToDocumentTypeDTO {
    private String id;
    private List<ContactType> contactTypes;

    @Data
    public static class ContactType {
        private String contactId;
        private DocumentType.Type type;
    }
}
