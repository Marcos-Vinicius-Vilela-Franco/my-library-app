package com.biblioteca.app.excecoes;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

   
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getDescription(false),ex.getMessage(),new Date());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadRequestUsuarioDto.class)
    public ResponseEntity<ExceptionResponse> HandlebadRequestException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getDescription(false),ex.getMessage(),new Date());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestLivroDto.class)
    public ResponseEntity<ExceptionResponse> HandlebadRequestExceptionLivro(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getDescription(false),ex.getMessage(),new Date());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundRequestUsuarioDTO.class)
    public ResponseEntity<ExceptionResponse> HandleNotFoundExceptionUsuario(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getDescription(false),ex.getMessage(),new Date());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundLivroDto.class)
    public ResponseEntity<ExceptionResponse> HandleNotFoundExceptionLivro(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(request.getDescription(false),ex.getMessage(),new Date());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

   
  

    

    // Outros manipuladores de exceção podem ser adicionados aqui
}