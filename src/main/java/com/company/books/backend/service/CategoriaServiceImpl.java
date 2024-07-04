package com.company.books.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

	@Autowired
	private ICategoriaDao categoriaDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() {

		log.info("inicio metodo buscarCategorias()");

		CategoriaResponseRest response = new CategoriaResponseRest();

		try {

			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();

			response.getCategoriaResponse().setCategoria(categoria);

			response.setMetada("Respuesta ok", "00", "Respuesta exitosa");

		} catch (Exception e) {
			response.setMetada("Respuesta nok", "-1", "Error al consulta categorias");
			log.error("error al consultar categorias: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); // devuelve 200
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
		log.info("inicio de busqueda por ID");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		try {
			Optional<Categoria> categoria = categoriaDao.findById(id);
			if (categoria.isPresent()) {
				list.add(categoria.get());
				response.getCategoriaResponse().setCategoria(list);
				response.setMetada("Respuesta ok", "00", "Respuesta exitosa");
			} else {
				log.error("Error en la consulta");
				response.setMetada("respuesta nok", "-1", "categoria no encotrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {

			log.error("Error en la consulta");
			response.setMetada("respuesta nok", "-1", "ERROR al consultar categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); // devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria) {
		log.info("inicio de creacion de categoria ");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		try {
			Categoria categoriaGuardada = categoriaDao.save(categoria);
			if (categoriaGuardada != null) {
				list.add(categoriaGuardada);
				response.getCategoriaResponse().setCategoria(list);
				response.setMetada("Respuesta ok", "00", "Respuesta exitosa");
			} else {
				log.error("Error al guardar la categoria");
				response.setMetada("respuesta nok", "-1", "categoria no guardada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error en la creacion");
			response.setMetada("respuesta nok", "-1", "ERROR al crear categoria");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetada("respuesta ok", "00", "Catagoria creada");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); // devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria request, Long id) {
		log.info("inicio de actualizacion de categoria ");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		try {
			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
			if (categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(request.getNombre());
				categoriaBuscada.get().setDescripcion(request.getDescripcion());
				Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());
				if (categoriaActualizar != null) {

					response.setMetada("respuesta ok", "00", "Catagoria actualizada");
					list.add(categoriaActualizar);
					response.getCategoriaResponse().setCategoria(list);
				} else {
					log.error("Error en actualizar categoria");
					response.setMetada("respuesta nok", "-1", "categoria no guardada");
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
				}

			} else {
				log.error("Error al actualizar la categoria");
				response.setMetada("respuesta nok", "-1", "categoria no guardada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.error("Error al actualizar la categoria", e.getMessage());
			e.getStackTrace();
			response.setMetada("respuesta nok", "-1", "categoria no actualizada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); // devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
		log.info("inicio el proceso de elimianr categoria");
		CategoriaResponseRest response = new CategoriaResponseRest();
		try {
			// eliminar el registro
			categoriaDao.deleteById(id);
			response.setMetada("respuesta ok", "00", "Catagoria eliminada");
		} catch (Exception e) {
			log.error("Error al eliminar la categoria", e.getMessage());
			e.getStackTrace();
			response.setMetada("respuesta nok", "-1", "categoria no eliminada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); // devuelve 200
	}

}
