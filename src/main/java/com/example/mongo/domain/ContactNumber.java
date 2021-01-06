package com.example.mongo.domain;

import lombok.Data;

@Data
public class ContactNumber {
    private String phoneCode;
    private String number;
    private String note;
}
