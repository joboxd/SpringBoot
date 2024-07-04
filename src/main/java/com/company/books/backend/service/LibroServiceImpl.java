package com.company.books.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDao;
import com.company.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceImpl implements ILibrosServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);
    @Autowired
    private ILibroDao iLibroDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> buscarTodosLibros() {
        log.info("Inicia proceso de busqueda de todos los libros");
        LibroResponseRest responseRest = new LibroResponseRest();
        try {

            List<Libro> libros = (List<Libro>) iLibroDao.findAll();
            if (libros.size() > 0) {
                responseRest.setMetada("ok", "200", "libros encontrados exitosamente");
                responseRest.getLibroResponse().setLibro(libros);
            } else {
                responseRest.setMetada("nok", "-1", "No hay libros para mostrar");
                responseRest.getLibroResponse().setLibro(libros);
            }

        } catch (Exception e) {
            e.getStackTrace();
            log.error("fallo la busqueda de los libros", e.getMessage());
            responseRest.setMetada("nok", "-1", "No se encontraron los libros");
            return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> buscarLibrosPorId(Long id) {
        log.info("Inicia proceso de busqueda por ID");
        LibroResponseRest responseRest = new LibroResponseRest();
        List<Libro> list = new ArrayList<>();
        Optional<Libro> libro = iLibroDao.findById(id);
        try {
            if (libro.isPresent()) {
                list.add(libro.get());
                responseRest.setMetada("ok", "00", "Se encontró el libro");
                responseRest.getLibroResponse().setLibro(list);
            } else {
                log.error("Error en la consulta");
                responseRest.setMetada("respuesta nok", "-1", "categoria no encotrada");
                return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error al intentar encontrar el libro con el ID" + id, e.getMessage());
            return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> crearLibro(Libro request) {
        log.info("Incia proceso de creacion de Libro");
        LibroResponseRest responseRest = new LibroResponseRest();
        List<Libro> list = new ArrayList<>();
        try {
            Libro libroParaGuardar = iLibroDao.save(request);
            if (libroParaGuardar != null) {
                list.add(libroParaGuardar);
                responseRest.getLibroResponse().setLibro(list);
                responseRest.setMetada("Respuesta ok", "00", "Respuesta exitosa");
            } else {
                log.error("Error al guardar el libro");
                responseRest.setMetada("respuesta nok", "-1", "categoria no guardada");
                return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            log.error("Error en la creacion");
            responseRest.setMetada("respuesta nok", "-1", "ERROR al crear categoria");
            return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> actualizarLibro(Libro request, Long id) {
        log.info("Inicia proceso actualizar libro");
        LibroResponseRest responseRest = new LibroResponseRest();
        List<Libro> list = new ArrayList<>();
        try {
            Optional<Libro> libroParaActualizar = iLibroDao.findById(id);
            if (libroParaActualizar.isPresent()) {
                libroParaActualizar.get().setNombre(request.getNombre());
                libroParaActualizar.get().setDescripcion(request.getDescripcion());
                libroParaActualizar.get().setCategoria(request.getCategoria());
                Libro libroActualizado = iLibroDao.save(libroParaActualizar.get());
                if (libroActualizado != null) {
                    responseRest.setMetada("respuesta ok", "00", "Catagoria actualizada");
                    list.add(libroActualizado);
                    responseRest.getLibroResponse().setLibro(list);
                } else {
                    log.error("Error en actualizar libro");
                    responseRest.setMetada("respuesta nok", "-1", "Libro no actualizada");
                    return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.BAD_REQUEST);
                }
            } else {
                log.error("Error al buscar el libro");
                responseRest.setMetada("respuesta nok", "-1", "categoria no encontrada");
                return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al actualizar el libro", e.getMessage());
            e.getStackTrace();
            responseRest.setMetada("respuesta nok", "-1", "libro no actualizado");
            return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<LibroResponseRest>(responseRest, HttpStatus.OK);

    }

}
