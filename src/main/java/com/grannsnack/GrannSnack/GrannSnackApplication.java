package com.grannsnack.GrannSnack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GrannSnackApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrannSnackApplication.class, args);
		//TODO Testing to create a password for a test user
		//System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}


}
