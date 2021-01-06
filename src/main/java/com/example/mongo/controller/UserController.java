package com.example.mongo.controller;

import com.example.mongo.configuration.*;
import com.example.mongo.domain.User;
import com.example.mongo.dto.AddUserDTO;
import com.example.mongo.dto.UpdateUserDTO;
import com.example.mongo.dto.UserCredentialDTO;
import com.example.mongo.dto.UserDTO;
import com.example.mongo.dto.testing.TestingDTO;
import com.example.mongo.service.UserService;
import com.example.mongo.util.AppUtils;
import com.example.mongo.util.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;
    private LocaleService localeService;
    private RequestMetaData requestMetaData;

    @PostMapping("/api/v1/user-register")
    public ResponseEntity<Object> addUser(@RequestBody AddUserDTO dto){
        User user = userService.addUser(dto);
        boolean isSaved = !Objects.isNull(user);
        return ResponseUtil.generate(isSaved, user,
                localeService.getMessage(isSaved ? MessageConstants.SAVED_SUCCESSFULLY : MessageConstants.FAILED));
    }

    @GetMapping("/api/v1/user-verify/{uuid}")
    public ResponseEntity<Object> userVerify(@PathVariable String uuid){
        boolean isVerified = userService.enableUser(uuid);
        return ResponseUtil.generate(isVerified, null,
                localeService.getMessage(isVerified ? MessageConstants.FETCHED_SUCCESSFULLY : MessageConstants.FAILED));
    }

    @PostMapping("/api/v1/user-login")
    public ResponseEntity<Object> login(@RequestBody UserCredentialDTO dto){
        Map<String, Object> data = userService.login(dto);
        return ResponseUtil.generate(true, data, localeService.getMessage(MessageConstants.FETCHED_SUCCESSFULLY));
    }

    @GetMapping("/api/v1/user-details")
    public ResponseEntity<Object> getUser(@CurrentUser LpoUser currentUser){
        log.info(":::::::: user id {}, email {}", currentUser.getUsername(), currentUser.getUserEmail());
        log.info(":::::::: request meta data, token {}", requestMetaData.getToken());
        UserDTO userDTO = userService.getUser(AppUtils.getUserId());
        return ResponseUtil.generate(true, userDTO, localeService.getMessage(MessageConstants.FETCHED_SUCCESSFULLY));
    }

    @PutMapping("/api/v1/user-details")
    public ResponseEntity<Object> updateUser(@RequestBody UpdateUserDTO dto){
        UserDTO userDTO = userService.updateUser(AppUtils.getUserId(), dto);
        return ResponseUtil.generate(true, userDTO, localeService.getMessage(MessageConstants.SAVED_SUCCESSFULLY));
    }

    @PostMapping("/api/v1/pub/test")
    public ResponseEntity<Object> testingDTO(@Valid @RequestBody TestingDTO dto){
        return ResponseUtil.generate(true, dto, localeService.getMessage(MessageConstants.FETCHED_SUCCESSFULLY));
    }

    @PostMapping("/api/v1/pub/param")
    public ResponseEntity<Object> testingParam(@RequestParam(required = false) String userId){
        userService.testParam("");
        return ResponseUtil.generate(true, userId, localeService.getMessage(MessageConstants.FETCHED_SUCCESSFULLY));
    }



}
