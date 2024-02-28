package com.zcourses.zcourses;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Z Courses",
		description = "Course API",
		version = "1"
))
public class ZcoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZcoursesApplication.class, args);
	}

}
