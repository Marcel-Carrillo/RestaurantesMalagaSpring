package edu.arelance.nube.service;

import java.util.List;
import java.util.Optional;

import edu.arelance.nube.repository.entity.Restaurante;

public interface RestauranteService {
	
	
	
	//iterable recorre
	Iterable<Restaurante> consultarTodos();
	
	//optional devuelve siempre algo que puede contener o no un restaurante
	//asi evitamos el null pointer exception
	Optional<Restaurante> consultarRestaurante (Long id);
	
	Restaurante altaRestaurante (Restaurante restaurante);
	
	void borrarRestaurante (Long id);
	
	Optional<Restaurante> modificarRestaurante (Long id, Restaurante restaurante);

	Iterable<Restaurante> findByPrecioMedioBetween(int precioMin, int precioMax);
	
	Iterable<Restaurante> buscarRestaurantePorPalabra (String palabraBuscada1,String palabraBuscada2,String palabraBuscada3);
	
	Iterable<Restaurante> buscarRestaurantePorClave (String clave);
	
	List<String> cuantosBarriosHay ();
	
	
	

}
