package com.example.mongo.dto;

import com.example.mongo.domain.Address;
import com.example.mongo.domain.Contact;
import com.example.mongo.domain.ContactNumber;
import com.example.mongo.enums.BelongsTo;
import com.example.mongo.enums.ContactAddress;
import com.example.mongo.enums.ContactType;
import com.example.mongo.enums.Gender;
import lombok.Data;

import java.time.ZoneId;
import java.util.Date;

@Data
public class AddContactDTO {
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

    public void toContact(Contact contact){
        contact.setDob(new Date(dob).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
    }
}
