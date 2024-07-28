package com.biblioteca.app.excecoes;

public class BadRequestLivroDto extends RuntimeException{
    public BadRequestLivroDto(String mensagem){
        super(mensagem);
    }
    
}
