package com.example.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class })
public class MongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoApplication.class, args);
	}

}
