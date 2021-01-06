package com.example.mongo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCredentialDTO {
    private String credential;
    private String password;
    private Type type;
    public static enum Type {
        EMAIL, USER_NAME;
    }
}
