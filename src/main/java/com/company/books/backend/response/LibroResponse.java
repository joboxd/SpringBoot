package com.company.books.backend.response;

import java.util.List;

import com.company.books.backend.model.Libro;

import lombok.Getter;
import lombok.Setter;;;
@Getter
@Setter
public class LibroResponse {
    private List<Libro> libro;
}
