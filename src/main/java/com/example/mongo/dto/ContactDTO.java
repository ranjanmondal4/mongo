package com.example.mongo.dto;

import com.example.mongo.domain.Address;
import com.example.mongo.domain.ContactNumber;
import com.example.mongo.enums.BelongsTo;
import com.example.mongo.enums.ContactAddress;
import com.example.mongo.enums.ContactType;
import com.example.mongo.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String primaryEmail;
    private Gender gender;
    private Long dob;
    private ContactNumber primaryContact;
    private Address primaryAddress;
    private boolean usCitizen;
    private boolean fiduciary;
    private boolean beneficiary;
    private ContactType contactType;
    private ContactAddress contactAddress;
    private BelongsTo belongsTo;
}
