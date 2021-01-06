package com.example.mongo.dto.emergency;

import com.example.mongo.domain.Contact;
import com.example.mongo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
//@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EmergencyCardDTO {
    private String id;
    private HolderInfo holderInfo;
    private Type holderType;
    private String allergies;
    private String medication;
    private Set<EmergencyContactDTO> emergencyContacts;

    public enum Type {
        CONTACT, USER;
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class HolderInfo {
        private String holderId;
        private String firstName;
        private String lastName;
        private String email;
    }

    @Data
    public static class EmergencyContactDTO {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String relation;
        private Type holderType;

        public static EmergencyContactDTO of(User user, String relation){
            EmergencyContactDTO dto = new EmergencyContactDTO();
            dto.id = user.getId();
            dto.firstName = user.getFirstName();
            dto.lastName = user.getLastName();
            dto.email = user.getPrimaryEmail().getEmail();
            dto.relation = relation;
            dto.holderType = Type.USER;
            return dto;
        }

        public static EmergencyContactDTO of(Contact contact, String relation){
            EmergencyContactDTO dto = new EmergencyContactDTO();
            dto.id = contact.getId();
            dto.firstName = contact.getFirstName();
            dto.lastName = contact.getLastName();
            dto.email = contact.getPrimaryEmail();
            dto.relation = relation;
            dto.holderType = Type.CONTACT;
            return dto;
        }
    }
}
