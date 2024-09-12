package com.msvc.producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoServiceApplication.class, args);
	}

}
