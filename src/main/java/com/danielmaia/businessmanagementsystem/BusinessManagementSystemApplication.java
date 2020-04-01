package com.danielmaia.businessmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class BusinessManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessManagementSystemApplication.class, args);
    }

}

