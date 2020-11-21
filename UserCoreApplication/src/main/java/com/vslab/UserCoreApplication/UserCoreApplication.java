package com.vslab.UserCoreApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableJpaRepositories("com.vslab.UserCoreApplication.Repository")
public class UserCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCoreApplication.class, args);
	}

}
