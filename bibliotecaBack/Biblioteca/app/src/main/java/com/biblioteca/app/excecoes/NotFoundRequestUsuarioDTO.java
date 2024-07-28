package com.biblioteca.app.excecoes;

public class NotFoundRequestUsuarioDTO extends RuntimeException {
    public NotFoundRequestUsuarioDTO(String mensagem) {
        super(mensagem);
    }
}
