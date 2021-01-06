package com.example.mongo.domain;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Email {
    @NonNull
    private String email;
    private boolean verified;
}
