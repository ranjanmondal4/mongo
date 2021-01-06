package com.example.mongo.configuration;

import lombok.ToString;

@ToString
public class DataNotFoundException extends RuntimeException {
    private String message;
    private DataNotFoundException(String message){
        super(message);
        this.message = message;
    }
    public static DataNotFoundException of(String message){
        return new DataNotFoundException(message);
    }
}
