package com.example.mongo.dto.testing;

import com.example.mongo.util.validators.ValidEmail;
import lombok.Data;

@Data
public class TestingDTO {
    /*@IpAddress(message = MessageConstants.INVALID_IP_ADDRESS)
    private String ip;*/
    @ValidEmail
    private String email;
}
