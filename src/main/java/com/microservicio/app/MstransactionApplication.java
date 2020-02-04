package com.microservicio.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MstransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MstransactionApplication.class, args);
	}

}
