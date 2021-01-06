package com.example.mongo.dto;

import com.example.mongo.domain.Contact;
import com.example.mongo.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    User addUserDTOtoUser(AddUserDTO dto);
    @Mappings({
            @Mapping(target="primaryEmail", source="primaryEmail"),
            @Mapping(target="gender", source="gender"),
            @Mapping(target="primaryContact", source="primaryContact"),
            @Mapping(target="primaryAddress", source="primaryAddress"),
            @Mapping(target="maritalStatus", source="maritalStatus"),
            @Mapping(target = "dob", ignore = true)
    })
    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(target="gender", source="gender"),
            @Mapping(target="primaryContact", source="primaryContact"),
            @Mapping(target="primaryAddress", source="primaryAddress"),
            @Mapping(target = "dob", ignore = true)
    })
    Contact addContactDtoToContact(AddContactDTO dto);

    @Mappings({
            @Mapping(target="gender", source="gender"),
            @Mapping(target="primaryContact", source="primaryContact"),
            @Mapping(target="primaryAddress", source="primaryAddress"),
            @Mapping(target = "dob", ignore = true)
    })
    ContactDTO contactToContactDTO(Contact contact);
}
