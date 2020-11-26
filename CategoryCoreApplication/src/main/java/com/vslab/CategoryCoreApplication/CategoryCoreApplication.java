package com.vslab.CategoryCoreApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;



@SpringBootApplication
@EnableEurekaClient
public class CategoryCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryCoreApplication.class, args);
	}

}
