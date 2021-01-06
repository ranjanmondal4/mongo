package com.example.mongo.domain;

public enum MaritalStatus {
    SINGLE("Single"), MARRIED("Married"), DIVORCED("Divorced"),
    WIDOW_WIDOWER("Widow/Widower");
    private String description;
    private MaritalStatus(String description){
        this.description = description;
    }
}
