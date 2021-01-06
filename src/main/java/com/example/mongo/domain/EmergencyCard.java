package com.example.mongo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;

@Data
@NoArgsConstructor(staticName = "of")
public class EmergencyCard extends BaseEntity {
    @DBRef
    private User user;
    @DBRef
    private Contact contact;
    private String allergies;
    private String medication;
    private Set<EmergencyContact> emergencyContacts;
}
