package com.example.mongo.service;

import com.example.mongo.configuration.DataNotFoundException;
import com.example.mongo.domain.User;
import com.example.mongo.domain.redis.UserInfo;
import com.example.mongo.domain.redis.UserUUID;
import com.example.mongo.dto.*;
import com.example.mongo.enums.UserStatus;
import com.example.mongo.enums.UserType;
import com.example.mongo.repo.UserInfoRepo;
import com.example.mongo.repo.UserRepo;
import com.example.mongo.repo.UserUUIDRepo;
import com.example.mongo.util.AppUtils;
import com.example.mongo.util.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
@Validated
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final UserInfoRepo userInfoRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserUUIDRepo userUUIDRepo;

    public User addUser(AddUserDTO dto){
        User user = userMapper.addUserDTOtoUser(dto);
        validateNewUser(dto);
        user.updateFields(dto.getEmail(), passwordEncoder.encode(dto.getPassword()), UserType.CLIENT);
        user = userRepo.save(user);
        addUserUUID(user.getId());
        return user;
    }

    public void validateNewUser(AddUserDTO dto){
        if(StringUtils.compare(dto.getPassword(), dto.getConfirmPassword()) != 0)
            throw DataNotFoundException.of(MessageConstants.PASSWORD_NOT_MATCHED);

        if(userRepo.findByPrimaryEmail_EmailAndPrimaryEmail_Verified(dto.getEmail(), true).isPresent()){
            throw DataNotFoundException.of(MessageConstants.USER_ALREADY_EXISTS);
        }
    }

    public void addUserUUID(String userId){
        userUUIDRepo.save(UserUUID.of(UUID.randomUUID().toString(), userId));
    }

    public boolean enableUser(String uuid) {
        UserUUID userUUID = userUUIDRepo.findById(uuid).orElseThrow(() -> DataNotFoundException.of(MessageConstants.INVALID_UUID));
        User user = userRepo.findById(userUUID.getUserId()).orElseThrow(() -> DataNotFoundException.of(MessageConstants.USER_NOT_FOUND));
        user.getPrimaryEmail().setVerified(true);
        user.setUserStatus(UserStatus.ACTIVE);
        userRepo.save(user);
        return true;
    }

    public Map<String, Object> login(UserCredentialDTO dto){
        Optional<User> userOptional = dto.getType() == UserCredentialDTO.Type.EMAIL ?
                userRepo.findByPrimaryEmail_EmailAndPrimaryEmail_Verified(dto.getCredential(), true)
                : userRepo.findByUserName(dto.getCredential());

        User user = userOptional.orElseThrow(() -> DataNotFoundException.of(MessageConstants.USER_NOT_FOUND));
        if(user.getUserStatus() != UserStatus.ACTIVE)
            throw DataNotFoundException.of(MessageConstants.USER_NOT_VERIFIED);

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw DataNotFoundException.of(MessageConstants.INVALID_PASSWORD);

        UUID token = UUID.randomUUID();
        UserInfo userInfo = UserInfo.of(user.getId(), token.toString(), Collections.emptyList());
        userInfoRepo.save(userInfo);
        return Collections.singletonMap("token", token);
    }

    public UserDTO getUser(String userId){
        User user = userRepo.findById(userId).orElseThrow(() -> DataNotFoundException.of(MessageConstants.USER_NOT_FOUND));
        return userToUserDTO(user);
    }

    public UserDTO updateUser(String userId, UpdateUserDTO dto){
        User user = userRepo.findById(userId).orElseThrow(() -> DataNotFoundException.of(MessageConstants.USER_NOT_FOUND));
        user = dto.toUser(user);
        user = userRepo.save(user);
        return userToUserDTO(user);
    }

    private UserDTO userToUserDTO(User user){
        UserDTO userDTO = userMapper.userToUserDTO(user);
        userDTO.setDob(Objects.isNull(user.getDob()) ? null : user.getDob().toEpochDay());
        return userDTO;
    }

    public Optional<User> findUserById(String userId){
        return userRepo.findById(userId);
    }

    public User findByUserId(String userId){
        return userRepo.findById(userId).orElseThrow(() -> DataNotFoundException.of(MessageConstants.USER_NOT_FOUND));
    }

    public boolean testParam(@NotBlank(message = MessageConstants.EMPTY_STRING) String userId){
        return true;
    }
}
