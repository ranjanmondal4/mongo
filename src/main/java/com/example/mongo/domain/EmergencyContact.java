package com.example.mongo.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class EmergencyContact {
    @DBRef
    private Contact contact;
    @DBRef
    private User user;
    private String relation;

    public static EmergencyContact of(Contact contact, String relation){
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.contact = contact;
        emergencyContact.relation = relation;
        return emergencyContact;
    }

    public static EmergencyContact of(User user, String relation){
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.user = user;
        emergencyContact.relation = relation;
        return emergencyContact;
    }
}
