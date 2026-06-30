package com.lovable.pedido_service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PedidoServiceApplication {


	//fuerza creacion del logger
	private static final Logger logger =
			LoggerFactory.getLogger(PedidoServiceApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(PedidoServiceApplication.class, args);
	}


	@PostConstruct
	public void init() {
		logger.info("ARCHIVO DE LOG FUNCIONANDO ✅");
	}



}
