package com.example.mongo.domain;

import com.example.mongo.enums.BelongsTo;
import com.example.mongo.enums.ContactAddress;
import com.example.mongo.enums.ContactType;
import com.example.mongo.enums.Gender;
import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document
public class Contact {
    @Id
    private String id;
    @Indexed
    @DBRef
    private User user;
    @NonNull
    private String firstName;
    private String lastName;
    @NonNull
    @Indexed
    private String primaryEmail;
    @NonNull
    private Gender gender = Gender.UNKNOWN;
    private LocalDate dob;
    private ContactNumber primaryContact;
    private Address primaryAddress;
    private boolean usCitizen;
    private boolean fiduciary;
    private boolean beneficiary;
    @NonNull
    private ContactType contactType;
    private ContactAddress contactAddress;
    private BelongsTo belongsTo;
}
