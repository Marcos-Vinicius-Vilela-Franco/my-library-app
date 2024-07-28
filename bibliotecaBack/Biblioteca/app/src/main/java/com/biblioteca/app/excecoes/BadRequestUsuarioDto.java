package com.biblioteca.app.excecoes;


public class BadRequestUsuarioDto extends RuntimeException {

    public BadRequestUsuarioDto(String exception){
        super(exception);
    }
}