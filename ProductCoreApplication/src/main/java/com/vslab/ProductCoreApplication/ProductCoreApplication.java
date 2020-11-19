package com.vslab.ProductCoreApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCoreApplication.class, args);
	}

}
