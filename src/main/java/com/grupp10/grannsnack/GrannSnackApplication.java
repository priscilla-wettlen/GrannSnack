package com.grupp10.grannsnack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrannSnackApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrannSnackApplication.class, args);
        System.out.println("kebab är gott");
    }

}
