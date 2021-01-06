package com.example.mongo.dto.emergency;

import com.example.mongo.domain.EmergencyCard;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class AddEmergencyCardDTO {
    private String id;
    private String contactId;
    private String allergies;
    private String medication;
    @NotEmpty
    private Set<EmergencyContactDTO> emergencyContacts;

    @Data
    @NoArgsConstructor
    public static class EmergencyContactDTO {
        @NotBlank
        private String id;
        @NotNull
        private Type type;
        @NotBlank
        private String relation;
    }

    public EmergencyCard toEmergencyCard(EmergencyCard card){
        card.setAllergies(allergies);
        card.setMedication(medication);
        return card;
    }

    public enum Type {
        CONTACT, USER;
    }
}


