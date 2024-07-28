package com.biblioteca.app.dtos;

import lombok.Data;

@Data
public class LivroDTO {
    private Long id;  
    private String titulo;
    private String autor;
    private Boolean emprestado;
}
