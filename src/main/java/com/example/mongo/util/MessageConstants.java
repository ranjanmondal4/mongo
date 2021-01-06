package com.example.mongo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageConstants {
    public static final String FETCHED_SUCCESSFULLY = "fetch.successfully";
    public static final String SAVED_SUCCESSFULLY = "saved.successfully";
    public static final String FAILED = "failed";
    public static final String PASSWORD_NOT_MATCHED = "password.not.matched";
    public static final String USER_ALREADY_EXISTS = "user.already.exists";
    public static final String USER_NOT_FOUND = "user.not.found";
    public static final String INVALID_UUID = "invalid.uuid";
    public static final String USER_NOT_VERIFIED = "user.not.verified";
    public static final String INVALID_PASSWORD = "invalid.password";
    public static final String CONTACT_NOT_FOUND = "contact.not.found";
    public static final String EMERGENCY_CARD_NOT_FOUND = "emergency-card.not.found";
    public static final String DATA_NOT_FOUND = "data.not.found";
    public static final String FOLDER_NOT_FOUND = "folder.not.found";
    public static final String FOLDER_ALREADY_EXISTS = "folder.already.exists";
    public static final String DOCUMENT_NOT_FOUND = "document.not.found";
    public static final String INVALID_IP_ADDRESS = "invalid.ip-address";
    public static final String INVALID_EMAIL = "invalid.email";
    public static final String EMPTY_STRING = "empty.string";
}
