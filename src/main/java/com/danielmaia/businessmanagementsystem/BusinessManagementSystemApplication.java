package com.danielmaia.businessmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BusinessManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessManagementSystemApplication.class, args);
    }

}
