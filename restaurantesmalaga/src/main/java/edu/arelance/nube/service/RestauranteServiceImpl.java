package edu.arelance.nube.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.arelance.nube.dto.FraseChuckNorris;
import edu.arelance.nube.repository.RestauranteRepository;
import edu.arelance.nube.repository.entity.Restaurante;
import net.bytebuddy.asm.Advice.OffsetMapping.Target.ForArray.ReadOnly;

@Service
public class RestauranteServiceImpl implements RestauranteService {

	// Es una instacia de la clase RestauranteRepository para poder usar sus
	// metodos.
	@Autowired
	RestauranteRepository restauranteRepository;

	@Override
	@Transactional(readOnly = true) // permitimos acceso concurrente a la tabla , varios a la vez
	public Iterable<Restaurante> consultarTodos() {
		return this.restauranteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Restaurante> consultarRestaurante(Long id) {
		return this.restauranteRepository.findById(id);
	}

	@Override
	@Transactional
	public Restaurante altaRestaurante(Restaurante restaurante) {
		return this.restauranteRepository.save(restaurante);
	}

	@Override
	@Transactional
	public void borrarRestaurante(Long id) {
		this.restauranteRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<Restaurante> modificarRestaurante(Long id, Restaurante restaurante) {
		Optional<Restaurante> opRest = Optional.empty();
		// 1 Leer
		opRest = this.restauranteRepository.findById(id);
		if (opRest.isPresent()) {
			// Al estar dentro de una transaccion, restauranteLeido esta asociado a un
			// registro de la tabla,
			// si modifico un campo estoy modificando la columna asociada (Estado
			// Persistent- (estandar) JPA)
			Restaurante restauranteLeido = opRest.get();
			// restauranteLeido.setNombre(restaurante.getNombre());
			BeanUtils.copyProperties(restaurante, restauranteLeido, "id", "creadoEn");
			opRest = Optional.of(restauranteLeido); // Relleno el optional
		}
		// 2 Actualizar

		return opRest;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Restaurante> findByPrecioMedioBetween(int precioMin, int precioMax) {
		Iterable<Restaurante> listaRestaPorPrecio = null;
		listaRestaPorPrecio = this.restauranteRepository.findByPrecioMedioBetween(precioMin, precioMax);

		return listaRestaPorPrecio;
	}
	
	//buscar por precio paginado
	@Override
	@Transactional(readOnly = true)
	public Page<Restaurante> buscarPorPrecioPaginado(int precioMin, int precioMax, Pageable pageable) {
		Iterable<Restaurante> listaRestaPorPrecio = null;
		listaRestaPorPrecio = this.restauranteRepository.findByPrecioMedioBetween(precioMin, precioMax, pageable);
		
		return (Page<Restaurante>) listaRestaPorPrecio;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Restaurante> buscarRestaurantePorPalabra(String palabraBuscada1, String palabraBuscada2,
			String palabraBuscada3) {
		Iterable<Restaurante> listaRestPalabra = null;
		listaRestPalabra = this.restauranteRepository.findByNombreOrBarrioOrEspecialidad1IgnoreCase(palabraBuscada1,
				palabraBuscada2, palabraBuscada3);

		return listaRestPalabra;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Restaurante> buscarRestaurantePorClave(String clave) {
		Iterable<Restaurante> listaRestPalabra = null;
		listaRestPalabra = this.restauranteRepository.buscarPorBarrioNombreOEspecialida(clave);

		return listaRestPalabra;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> cuantosBarriosHay() {
		List<String> listaBarrios = null;
		listaBarrios = this.restauranteRepository.cuantosBarriosHay();

		return listaBarrios;
	}

	@Override
	public Optional<FraseChuckNorris> obtenerFraseChuckNurris() {
		Optional<FraseChuckNorris> opChuck = Optional.empty();
		FraseChuckNorris fraseChuckNorris = null;
		RestTemplate restTemplate = null;
		restTemplate = new RestTemplate();
		fraseChuckNorris = restTemplate.getForObject("https://api.chucknorris.io/jokes/random", FraseChuckNorris.class);
		opChuck = Optional.of(fraseChuckNorris);

		return opChuck;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Restaurante> consultarPorPagina(Pageable pageable) {
		
		return this.restauranteRepository.findAll(pageable);
	}

}
