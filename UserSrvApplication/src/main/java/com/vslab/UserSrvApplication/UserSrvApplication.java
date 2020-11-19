package com.vslab.UserSrvApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSrvApplication.class, args);
	}

}
