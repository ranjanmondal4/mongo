package com.example.mongo.service;

import com.example.mongo.configuration.DataNotFoundException;
import com.example.mongo.domain.Contact;
import com.example.mongo.domain.EmergencyCard;
import com.example.mongo.domain.EmergencyContact;
import com.example.mongo.domain.User;
import com.example.mongo.dto.AddContactDTO;
import com.example.mongo.dto.ContactDTO;
import com.example.mongo.dto.UserMapper;
import com.example.mongo.dto.emergency.AddEmergencyCardDTO;
import com.example.mongo.dto.emergency.EmergencyCardDTO;
import com.example.mongo.repo.ContactRepo;
import com.example.mongo.repo.EmergencyCardRepo;
import com.example.mongo.util.MessageConstants;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContactService {
    private UserService userService;
    private UserMapper userMapper;
    private ContactRepo contactRepo;
    private EmergencyCardRepo emergencyCardRepo;

    public ContactDTO addContact(String userId, AddContactDTO dto){
        Contact contact = populateContactFields(userId, dto);
        contact = contactRepo.save(contact);
        return contactToContactDTO(contact);
    }

    private Contact populateContactFields(String userId, AddContactDTO dto){
        User user = userService.findUserById(userId)
                .orElseThrow(() -> DataNotFoundException.of(MessageConstants.USER_NOT_FOUND));
        Contact contact = userMapper.addContactDtoToContact(dto);
        dto.toContact(contact);
        contact.setUser(user);
        return contact;
    }

    public ContactDTO updateContact(String userId, String contactId, AddContactDTO dto){
        Contact contact = populateContactFields(userId, dto);
        contact.setId(contactId);
        contact = contactRepo.save(contact);
        return contactToContactDTO(contact);
    }

    public ContactDTO getContact(String userId, String contactId){
        Contact contact = contactRepo.findById(contactId)
                .orElseThrow(() -> DataNotFoundException.of(MessageConstants.CONTACT_NOT_FOUND));
        if(!contact.getUser().getId().equals(userId))
            throw DataNotFoundException.of(MessageConstants.CONTACT_NOT_FOUND);

        return contactToContactDTO(contact);
    }


    private ContactDTO contactToContactDTO(Contact contact){
        ContactDTO contactDTO = userMapper.contactToContactDTO(contact);
        contactDTO.setDob(Objects.isNull(contact.getDob()) ? null : contact.getDob().toEpochDay());
        return contactDTO;
    }

    public EmergencyCard addEmergencyCard(String userId, AddEmergencyCardDTO dto){
        EmergencyCard card;
        if(StringUtils.isEmpty(dto.getId())){
            card = EmergencyCard.of();
        }else {
            card = emergencyCardRepo.findById(dto.getId())
                    .orElseThrow(() -> DataNotFoundException.of(MessageConstants.EMERGENCY_CARD_NOT_FOUND));
        }
        card = dto.toEmergencyCard(card);
        User user = userService.findByUserId(userId);
        card.setUser(user);
        if(!StringUtils.isEmpty(dto.getContactId())) {
            Contact contact = contactRepo.findById(dto.getContactId())
                    .orElseThrow(() -> DataNotFoundException.of(MessageConstants.CONTACT_NOT_FOUND));
            card.setContact(contact);
        }
        card.setEmergencyContacts(addEmergencyContacts(user, dto.getEmergencyContacts()));
        return emergencyCardRepo.save(card);
    }

    private Set<EmergencyContact> addEmergencyContacts(User user, Set<AddEmergencyCardDTO.EmergencyContactDTO> emergencyContactDTOs){
       return emergencyContactDTOs.stream().map(ele -> {
            if(ele.getType() == AddEmergencyCardDTO.Type.CONTACT){
                Contact contact = contactRepo.findById(ele.getId()).orElseThrow(() -> DataNotFoundException.of(MessageConstants.CONTACT_NOT_FOUND));
                return EmergencyContact.of(contact, ele.getRelation());
            }else {
                return EmergencyContact.of(user, ele.getRelation());
            }
        }).collect(Collectors.toSet());
    }

    public Set<EmergencyCardDTO> getEmergencyCardsByUserId(String userId){
        List<EmergencyCard> cards = emergencyCardRepo.findByUser_Id(userId);
        return cards.stream().map(card -> {
            if(Objects.isNull(card.getContact())){
                User holder = card.getUser();
                EmergencyCardDTO.HolderInfo holderInfo = EmergencyCardDTO.HolderInfo.of(holder.getId(),
                        holder.getFirstName(), holder.getLastName(), holder.getPrimaryEmail().getEmail());
                return EmergencyCardDTO.of(card.getId(), holderInfo, EmergencyCardDTO.Type.USER, card.getAllergies(),
                        card.getMedication(), getEmergencyContacts(card));
            } else {
                Contact holder = card.getContact();
                EmergencyCardDTO.HolderInfo holderInfo = EmergencyCardDTO.HolderInfo.of(holder.getId(),
                        holder.getFirstName(), holder.getLastName(), holder.getPrimaryEmail());
                return EmergencyCardDTO.of(card.getId(), holderInfo, EmergencyCardDTO.Type.CONTACT, card.getAllergies(),
                        card.getMedication(), getEmergencyContacts(card));
            }
        }).collect(Collectors.toSet());
    }

    private Set<EmergencyCardDTO.EmergencyContactDTO> getEmergencyContacts(EmergencyCard card){
        return card.getEmergencyContacts().stream().map(emergencyContact -> {
            Contact contact = emergencyContact.getContact();
            if(Objects.isNull(contact)){
                return EmergencyCardDTO.EmergencyContactDTO.of(emergencyContact.getUser(), emergencyContact.getRelation());
            }else {
                return EmergencyCardDTO.EmergencyContactDTO.of(emergencyContact.getContact(), emergencyContact.getRelation());
            }
        }).collect(Collectors.toSet());
    }

    public List<Contact> findContactsByIdInAndUserId(List<String> contactIds, String userId){
        return contactRepo.findByIdInAndUser_Id(contactIds, userId);
    }
}
