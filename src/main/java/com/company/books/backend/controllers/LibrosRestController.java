package com.company.books.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;
import com.company.books.backend.service.ILibrosServiceImpl;

@RestController
@RequestMapping("/v1")
public class LibrosRestController {

    @Autowired
    private ILibrosServiceImpl service;

    @GetMapping("/findAllBooks")
    public ResponseEntity<LibroResponseRest> buscarTodosLibros() {
        return service.buscarTodosLibros();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<LibroResponseRest> buscarPorId(@PathVariable Long id) {
        return service.buscarLibrosPorId(id);
    }

    @PostMapping("/saveBook")
    public ResponseEntity<LibroResponseRest> guardarLibro(@RequestBody Libro libro) {
        return service.crearLibro(libro);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<LibroResponseRest> actualizarLibro(@RequestBody Libro libro, @PathVariable Long id) {
        return service.actualizarLibro(libro, id);
    }
}
