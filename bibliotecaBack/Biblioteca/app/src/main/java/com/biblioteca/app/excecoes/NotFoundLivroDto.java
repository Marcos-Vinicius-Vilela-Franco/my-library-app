package com.biblioteca.app.excecoes;

public class NotFoundLivroDto  extends RuntimeException {

    public NotFoundLivroDto(String exception){
        super(exception);
    }
}