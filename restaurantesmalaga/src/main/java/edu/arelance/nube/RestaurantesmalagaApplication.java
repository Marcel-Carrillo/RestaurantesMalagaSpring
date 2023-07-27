package edu.arelance.nube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@Controller
//@EnableEurekaClient// activamos el cliente eureka 
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
	
	@RequestMapping(value = "{path:[^\\.]*}", method = RequestMethod.GET)
    public String redirect() {
        return "forward:/";
    }

}

