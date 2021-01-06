package com.example.mongo.dto.documents;

import com.example.mongo.domain.Contact;
import com.example.mongo.domain.documents.DocumentType;
import com.example.mongo.enums.DocumentRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class DocumentTypeDTO {
    private String id;
    private String name;
    private DocumentRole role;
    private List<DocumentType.Subsection> subsections;
    private boolean byDefault;
    private boolean enable;
    private List<ContactType> contactTypes;

    @Data
    public static class ContactType {
        private String contactId;
        private String firstName;
        private String lastName;
        private String email;
        private DocumentType.Type type;
        public static ContactType of(Contact contact, DocumentType.Type type){
            ContactType contactType = new ContactType();
            contactType.contactId = contact.getId();
            contactType.firstName = contact.getFirstName();
            contactType.lastName = contact.getLastName();
            contactType.email = contact.getPrimaryEmail();
            contactType.type = type;
            return contactType;
        }
    }
}
