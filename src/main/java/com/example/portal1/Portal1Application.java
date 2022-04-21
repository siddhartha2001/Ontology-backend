package com.example.portal1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class Portal1Application {

	public static void main(String[] args) {
		SpringApplication.run(Portal1Application.class, args);
	}

}
