package com.company.books.backend.service;

import org.springframework.http.ResponseEntity;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;

public interface ILibrosServiceImpl {
    public ResponseEntity<LibroResponseRest> buscarTodosLibros();

    public ResponseEntity<LibroResponseRest> buscarLibrosPorId(Long id);

    public ResponseEntity<LibroResponseRest> crearLibro(Libro request);

    public ResponseEntity<LibroResponseRest> actualizarLibro(Libro request, Long id);

    public ResponseEntity<LibroResponseRest> borrarLibro(Long id);
}
