package com.example.mongo.domain;

import com.example.mongo.enums.Gender;
import com.example.mongo.enums.UserStatus;
import com.example.mongo.enums.UserType;
import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Document
@Data
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @NonNull
    private String firstName;
    private String lastName;
    @NonNull
    @Indexed
    private Email primaryEmail;
    @NonNull
    @Indexed
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private UserStatus userStatus;
    @NonNull
    private Set<UserType> userTypes;
    @NonNull
    private Gender gender = Gender.UNKNOWN;
    private LocalDate dob;
    private ContactNumber primaryContact;
    private Address primaryAddress;
    private MaritalStatus maritalStatus;
    private boolean hasChildren;
    private boolean usVeteran;
    private boolean usCitizen;

    public void updateFields(String primaryEmail, String password, UserType userType){
        this.userStatus = UserStatus.NOT_VERIFIED;
        this.userName = UUID.randomUUID().toString();
        this.userTypes = Collections.singleton(userType);
        this.primaryEmail = Email.of(primaryEmail, false);
        this.password = password;
    }
}
