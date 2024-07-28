package com.biblioteca.app.excecoes;

import java.util.Date;

public record ExceptionResponse(String details, String mensagem, Date data) {}
