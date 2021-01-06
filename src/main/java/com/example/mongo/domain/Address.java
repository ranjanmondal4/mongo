package com.example.mongo.domain;

import lombok.Data;

@Data
public class Address {
    private String addressLine;
    private String city;
    private String state;
    private String zipcode;
}
