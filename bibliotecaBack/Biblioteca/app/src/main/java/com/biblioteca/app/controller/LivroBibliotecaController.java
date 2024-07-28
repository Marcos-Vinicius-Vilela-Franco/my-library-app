package com.biblioteca.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.app.dtos.LivroDTO;
import com.biblioteca.app.dtos.NomeAutorDto;
import com.biblioteca.app.services.BibliotecaLivroService;
import com.biblioteca.app.utils.ValidationLivroDto;

@RestController
@RequestMapping("/lib/books")
@CrossOrigin(origins = "http://localhost:3000")
public class LivroBibliotecaController {
    
    @Autowired
    private ValidationLivroDto validationLivroDto;

    @Autowired
    private BibliotecaLivroService bibliotecaLivroService;


    @GetMapping("/get")
    public ResponseEntity<List<LivroDTO>> getLivros(){
        List<LivroDTO> livros = bibliotecaLivroService.getAllLivros();
            return ResponseEntity.ok(livros);
    }

    @GetMapping("/get/autor")
    public ResponseEntity<List<LivroDTO>> getLivroByAutor(@RequestBody NomeAutorDto autor){
        validationLivroDto.validaAutor(autor);
        List<LivroDTO> livros = bibliotecaLivroService.getLivroByAutor(autor.getAutor());
        return new ResponseEntity<>(livros,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<LivroDTO> criarLivro(@RequestBody LivroDTO livroDTO) {
        validationLivroDto.validarLivroDTO(livroDTO);
        LivroDTO livroAdicionado = bibliotecaLivroService.addLivro(livroDTO);
        return new ResponseEntity<>(livroAdicionado,HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<LivroDTO> atualizaLivro(@RequestBody LivroDTO livroDTO) {
        validationLivroDto.validarUsuarioDtoToUpdate(livroDTO);
        LivroDTO livroAdicionado = bibliotecaLivroService.updateLivro(livroDTO);
        return new ResponseEntity<>(livroAdicionado,HttpStatus.OK);
    }

   @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody LivroDTO livroDTO){
        try {
            validationLivroDto.existsById(livroDTO.getId());
            bibliotecaLivroService.deleteLivro(livroDTO.getId());
            return new ResponseEntity<>("Livro deletado!", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            // Captura a exceção de violação de integridade referencial
            validationLivroDto.MensageErroDelete();
            return new ResponseEntity<>("Não é possível excluir o livro. Existem empréstimos associados a este livro.", HttpStatus.BAD_REQUEST);
        }
    }


}
