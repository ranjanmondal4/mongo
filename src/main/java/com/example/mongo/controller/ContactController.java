package com.example.mongo.controller;

import com.example.mongo.configuration.LocaleService;
import com.example.mongo.configuration.ResponseUtil;
import com.example.mongo.domain.EmergencyCard;
import com.example.mongo.dto.AddContactDTO;
import com.example.mongo.dto.ContactDTO;
import com.example.mongo.dto.emergency.AddEmergencyCardDTO;
import com.example.mongo.dto.emergency.EmergencyCardDTO;
import com.example.mongo.service.ContactService;
import com.example.mongo.util.AppUtils;
import com.example.mongo.util.MessageConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class ContactController {
    private final ContactService contactService;
    private final LocaleService localeService;

    @PostMapping("/api/v1/contact")
    public ResponseEntity<Object> addContact(@RequestBody AddContactDTO dto){
        ContactDTO contactDTO = contactService.addContact(AppUtils.getUserId(), dto);
        return ResponseUtil.generate(true, contactDTO, localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY));
    }
    /*@PostMapping("/api/v1/contact")
    public Mono<ResponseEntity<Object>> addContact(@RequestBody AddContactDTO dto){
        ContactDTO contactDTO = contactService.addContact(AppUtils.getUserId(), dto);
        return ResponseUtil.generateMono(true, contactDTO, localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY));
    }*/

    @PutMapping("/api/v1/contact/{contactId}")
    public ResponseEntity<Object> updateContact(@PathVariable String contactId, @RequestBody AddContactDTO dto){
        ContactDTO contactDTO = contactService.updateContact(AppUtils.getUserId(), contactId, dto);
        return ResponseUtil.generate(true, contactDTO, localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY));
    }

    @GetMapping("/api/v1/contact/{contactId}")
    public ResponseEntity<Object> getContact(@PathVariable String contactId){
        ContactDTO contactDTO = contactService.getContact(AppUtils.getUserId(), contactId);
        return ResponseUtil.generate(true, contactDTO, localeService.getMessage(MessageConstants.FETCHED_SUCCESSFULLY));
    }

    @PostMapping("/api/v1/emergency-card")
    public ResponseEntity<Object> addEmergencyCard(@RequestBody AddEmergencyCardDTO dto){
        EmergencyCard card =  contactService.addEmergencyCard(AppUtils.getUserId(), dto);
        return ResponseUtil.generate(true, card, localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY));
    }

    @GetMapping("/api/v1/emergency-cards")
    public ResponseEntity<Object> getEmergencyCards(){
        Set<EmergencyCardDTO> cards = contactService.getEmergencyCardsByUserId(AppUtils.getUserId());
        return ResponseUtil.generate(true, cards, localeService.getMessage(MessageConstants.FETCHED_SUCCESSFULLY));
    }


}
