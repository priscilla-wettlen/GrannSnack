package com.grannsnack.GrannSnack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main driver of the program. It starts the springapplication which in turn drives our program.
 */

@SpringBootApplication
public class GrannSnackApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrannSnackApplication.class, args);
	}

}
