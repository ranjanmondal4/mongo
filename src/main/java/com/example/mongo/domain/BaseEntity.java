package com.example.mongo.domain;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class BaseEntity {
    @Id
    private String id;
    @NonNull
    protected boolean deleted = false;
}
