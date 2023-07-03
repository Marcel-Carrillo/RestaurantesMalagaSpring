package edu.arelance.nube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayProjectApplication {
	
	/*
	 * Para hacer el gateway
	 * 1 nuevo proyecto con spring starter proyect con las dependencias de gateway y eureka client
	 * 2 anotamos con @enableeurekaclient en el main
	 * 3 properties con configuracion en formato yml y enrutamiento
	 */

	public static void main(String[] args) {
		SpringApplication.run(GatewayProjectApplication.class, args);
	}

}
