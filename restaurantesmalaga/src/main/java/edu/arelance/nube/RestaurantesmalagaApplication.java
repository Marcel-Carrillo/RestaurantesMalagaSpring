package edu.arelance.nube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient// activamos el cliente eureka 
public class RestaurantesmalagaApplication {
	
	/*
	 * Para configurar el cliente eureka
	 * 1 add starters, eureka client
	 * 2 add anotacion enableEurekaClient
	 * 3 configuramos las properties relativas a eureka
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(RestaurantesmalagaApplication.class, args);
	}

}
