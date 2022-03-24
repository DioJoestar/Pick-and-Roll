package com.pickandroll.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //dev();
        SpringApplication.run(Application.class, args);
    }
}
