package com.example.mongo.dto;

import com.example.mongo.domain.Address;
import com.example.mongo.domain.ContactNumber;
import com.example.mongo.domain.MaritalStatus;
import com.example.mongo.domain.User;
import com.example.mongo.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Data
@NoArgsConstructor
public class UpdateUserDTO {
    private Gender gender;
    private Long dob;
    private ContactNumber primaryContact;
    private Address primaryAddress;
    private MaritalStatus maritalStatus;
    private boolean hasChildren;
    private boolean usVeteran;
    private boolean usCitizen;

    public User toUser(User user){
        user.setGender(gender);
        user.setDob(new Date(dob).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        user.setPrimaryContact(primaryContact);
        user.setPrimaryAddress(primaryAddress);
        user.setMaritalStatus(maritalStatus);
        user.setHasChildren(hasChildren);
        user.setUsCitizen(usCitizen);
        user.setUsVeteran(usVeteran);
        return user;
    }
}
