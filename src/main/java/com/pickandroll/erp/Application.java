package com.pickandroll.erp;

import com.pickandroll.erp.utils.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //dev();
        SpringApplication.run(Application.class, args);
    }

    private static void dev() {
        Utils u = new Utils();
        System.out.println("Dev pass: " + u.encrypPasswd("12345"));
    }
}
