package edu.arelance.nube.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Esta clase esta escuchando todas las excepciones de este paquete
@RestControllerAdvice(basePackages = { "edu.arelance.nube" })
public class GestionDeExcepciones {
	
	Logger logger = LoggerFactory.getLogger(GestionDeExcepciones.class);


	// para cada tipo de excepcion / defino un metodo
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public ResponseEntity<?> gestionStringOutIndexException(StringIndexOutOfBoundsException e) {
		ResponseEntity<?> responseEntity = null;

		responseEntity = ResponseEntity.internalServerError().body(e.getMessage());
		
		return responseEntity;
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> gestionExcepcionGenerica(Throwable e) {
		ResponseEntity<?> responseEntity = null;

		responseEntity = ResponseEntity.internalServerError().body(e.getMessage());
		logger.error(e.getMessage(), e);

		return responseEntity;
	}

}
