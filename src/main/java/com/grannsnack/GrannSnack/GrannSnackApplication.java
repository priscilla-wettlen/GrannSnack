package com.grannsnack.GrannSnack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GrannSnackApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrannSnackApplication.class, args);
	}

}
