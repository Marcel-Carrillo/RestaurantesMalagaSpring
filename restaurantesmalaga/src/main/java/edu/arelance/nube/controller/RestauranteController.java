package edu.arelance.nube.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.arelance.nube.repository.entity.Restaurante;
import edu.arelance.nube.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * API WEB HTTP -> Deriva en la ejecucion de un método a traves de TOMCAT(recibe
 * la peticion y enruta) y SPRING()
 *
 * GET -----> (consulta) TODOS http://localhost:8081/restaurante GET ----->
 * (consulta) Uno (por id) http://localhost:8081/restaurante/id POST ---->
 * (inserta) Un restaurante nuevo http://localhost:8081/restaurante (body
 * restaurante) PUT -----> (modifica) Un restaurante que ya existe
 * http://localhost:8081/restaurante/id (body restaurante) DELETE --> (elimina)
 * Un restaurante por (ID) http://localhost:8081/restaurante/id (consulta) por
 * barrio, por especialidad, por nombre, etc... anotacion @transactional para
 * controlar que los procesos de un metodo que interactuan BD se efectuen
 * completamente, si se hacen bien commit y se insertan los datos si no rollback
 * y no se hace nada.
 */

//@Controller //devuelve una vista (html/jsp)
@RestController // devuelve un JSON (permite un uso mas dinamico de los datos de respuesta para
//despues representarlos o no, en definitiva es mas dinamico  http://localhost:8081/restaurante
@RequestMapping("/restaurante")
public class RestauranteController {

	@Autowired
	RestauranteService restauranteService;
	
	@Autowired
	Environment enviroment; //voy a sacar la info del puerto 

	Logger logger = LoggerFactory.getLogger(RestauranteController.class);

	@GetMapping("/test") // GET http://localhost:8081/restaurante/test
	public Restaurante obtenerRestauranteTest() {
		Restaurante restaurante = null;

		System.out.println("llamando a obtenerRestauranteTest");
		logger.debug("Estoy en el obtenerRestauranteTest");
		restaurante = new Restaurante(1l, "Martinete", "Carlos Haya 33", "Carranque", "www.martinete.org",
				"http://google.xe", 33.65f, -2.3f, 10, "gazpachuelo", "paella", "sopa de marisco", LocalDateTime.now());

		return restaurante;
	}

	// Consultar todos. Metodo GET a http://localhost:8081/restaurante/
	@GetMapping
	public ResponseEntity<?> obtenerListadoRestaurantes() {

		ResponseEntity<?> responseEntity = null;

		Iterable<Restaurante> lista_restaurantes = null;
		
		logger.debug("Atendido por el puerto" + enviroment.getProperty("local.server.port"));

		lista_restaurantes = this.restauranteService.consultarTodos();

		responseEntity = ResponseEntity.ok(lista_restaurantes);

		return responseEntity;
	}

	// Consultar 1 restaurante

	@Operation(description = "Este servicio permite la consulta de 1 restaurante por un id")
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Restaurante> or = null;

		logger.debug("en listarPorId" + id);
		or = this.restauranteService.consultarRestaurante(id);
		if (or.isPresent()) {
			// la consulta a recuperado un registro
			Restaurante restauranteLeido = or.get();
			responseEntity = ResponseEntity.ok(restauranteLeido);
			logger.debug("recuperado el registo " + restauranteLeido);
		} else {
			// la consulta no ha recuperado un registro
			responseEntity = ResponseEntity.noContent().build();
			logger.debug("El restaurante con " + id + " no existe");
		}

		logger.debug("Saliendo de listarPorId");
		return responseEntity;
	}

	// Delete restaurante
	@DeleteMapping("/{id}")
	public ResponseEntity<?> borrarPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		this.restauranteService.borrarRestaurante(id);
		responseEntity = ResponseEntity.ok().build();

		return responseEntity;
	}

	// Post insertar en la base de datos 1 rest
	@PostMapping
	public ResponseEntity<?> insertarRestaurante(@Valid @RequestBody Restaurante restaurante,
			BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		Restaurante restauranteNuevo = null;

		// TODO validar
		if (bindingResult.hasErrors()) {
			logger.debug("Errores en la entrada POST");
			responseEntity = generarRespuestaErroresValidacion(bindingResult);
		} else {
			logger.debug("SIN errores en la entrada POST");
			restauranteNuevo = this.restauranteService.altaRestaurante(restaurante);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(restauranteNuevo);
		}

		return responseEntity;
	}

	private ResponseEntity<?> generarRespuestaErroresValidacion(BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> listaErrores = null;

		listaErrores = bindingResult.getAllErrors();
		// imprimir los errores por el log
		listaErrores.forEach(e -> logger.error(e.toString()));

		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);

		return responseEntity;
	}

	/*
	 * @PutMapping("/{id}") public ResponseEntity<?>
	 * modificarRestaurante(@Valid @RequestBody Restaurante
	 * restaurante, @PathVariable Long id, BindingResult bindingResult) {
	 * 
	 * ResponseEntity<?> responseEntity = null; Optional<Restaurante> opRest = null;
	 * if (bindingResult.hasErrors()) { logger.debug("Error en la entrada al PUT");
	 * responseEntity = generarRespuestaDeErroresValidacion(bindingResult);
	 * 
	 * } else { logger.debug("SIN Error en la entrada al PUT"); opRest =
	 * this.restauranteService.modificarRestaurante(id, restaurante); if
	 * (opRest.isPresent()) { Restaurante rm = opRest.get(); //rm -> restaurante
	 * modificado que nos llega del service responseEntity = ResponseEntity.ok(rm);
	 * } else { responseEntity =
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).build(); } } return
	 * responseEntity; }
	 */

	// Put modifica en la base de datos 1 rest
	@PutMapping("/{id}")
	public ResponseEntity<?> modificarRestaurante(@Valid @RequestBody Restaurante restaurante, @PathVariable Long id,
			BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		Optional<Restaurante> opRest = null;

		if (bindingResult.hasErrors()) {
			logger.debug("Errores en el PUT");
			responseEntity = generarRespuestaErroresValidacion(bindingResult);
		} else {

			logger.debug("SIN Errores en el PUT");
			opRest = this.restauranteService.modificarRestaurante(id, restaurante);
			if (opRest.isPresent()) {
				Restaurante rm = opRest.get();
				responseEntity = ResponseEntity.ok(rm);
			} else {
				responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}

		return responseEntity;
	}

	// Get ver restaurantes por rango de precios GET
	// http://localhost:8081/restaurante/buscarPorPrecio/1/4
	@GetMapping("/buscarPorPrecio/{precioMin}/{precioMax}")
	public ResponseEntity<?> findByPrecioMedioBetween(@PathVariable int precioMin, @PathVariable int precioMax) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> listaPorPrecio = null;

		listaPorPrecio = this.restauranteService.findByPrecioMedioBetween(precioMin, precioMax);
		responseEntity = ResponseEntity.ok(listaPorPrecio);

		return responseEntity;
	}

	// Get ver restaurantes por palabra GET
	// http://localhost:8081/restaurante/buscarPorPalabra/arroz/arroz/arroz
	@GetMapping("/buscarPorPalabra/{palabraBuscada1}/{palabraBuscada2}/{palabraBuscada3}")
	public ResponseEntity<?> buscarRestaurantePorPalabra(@PathVariable String palabraBuscada1,
			@PathVariable String palabraBuscada2, @PathVariable String palabraBuscada3) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> listaPorPalabra = null;

		listaPorPalabra = this.restauranteService.buscarRestaurantePorPalabra(palabraBuscada1, palabraBuscada2,
				palabraBuscada3);
		responseEntity = ResponseEntity.ok(listaPorPalabra);

		return responseEntity;
	}

	// Get ver restaurantes por clave GET
	// http://localhost:8081/restaurante/buscarPorClave/arroz
	@GetMapping("/buscarPorClave/{clave}")
	public ResponseEntity<?> buscarPorClave(@PathVariable String clave) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Restaurante> listaPorPalabra = null;

		listaPorPalabra = this.restauranteService.buscarRestaurantePorClave(clave);
		responseEntity = ResponseEntity.ok(listaPorPalabra);

		return responseEntity;
	}

	// Consultar todos lo barrios. Metodo GET a
	// http://localhost:8081/restaurante/barrios
	@GetMapping("/barrios")
	public ResponseEntity<?> obtenerListadoBarrios() {

		ResponseEntity<?> responseEntity = null;

		List<String> lista_barrios = null;

		lista_barrios = this.restauranteService.cuantosBarriosHay();

		responseEntity = ResponseEntity.ok(lista_barrios);

		return responseEntity;
	}

}
