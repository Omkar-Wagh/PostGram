package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class PostGramApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostGramApplication.class, args);
		System.out.println("Hello Learners");
	}

}
