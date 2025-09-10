package com.example.demo;

import org.springframework.boot.SpringApplication;

public class TestCollegeAdmissionApplication {

	public static void main(String[] args) {
		SpringApplication.from(CollegeAdmissionApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
