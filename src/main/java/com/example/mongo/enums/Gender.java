package com.example.mongo.enums;

public enum Gender {
    MALE("Male"), FEMALE("Female"), OTHER("Other"), UNKNOWN("Unknown");
    private String description;
    private Gender(String description){
        this.description = description;
    }
}
