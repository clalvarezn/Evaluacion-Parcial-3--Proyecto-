package com.lovable.boleta_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BoletaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoletaServiceApplication.class, args);
	}

}
