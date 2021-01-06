package com.example.mongo.dto;

import com.example.mongo.domain.Address;
import com.example.mongo.domain.ContactNumber;
import com.example.mongo.domain.Email;
import com.example.mongo.domain.MaritalStatus;
import com.example.mongo.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private Email primaryEmail;
    private Gender gender;
    private Long dob;
    private ContactNumber primaryContact;
    private Address primaryAddress;
    private MaritalStatus maritalStatus;
    private boolean hasChildren;
    private boolean usVeteran;
    private boolean usCitizen;
}
