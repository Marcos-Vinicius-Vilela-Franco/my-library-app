package com.biblioteca.app.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.app.dtos.EmprestimosDTO;
import com.biblioteca.app.services.BibliotecaEmprestimoService;
import com.biblioteca.app.utils.ValiadtionEmprestimoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("lib/emprestimo")
@CrossOrigin(origins = "http://localhost:3000")
public class EmprestimoBibliotecaController {
    

    @Autowired
    private ValiadtionEmprestimoDto valiadtionEmprestimoDto;

    @Autowired
    private BibliotecaEmprestimoService bibliotecaEmprestimoService;

    @GetMapping("/getall")
    public ResponseEntity<List<EmprestimosDTO>> getAllEmprestimos(){
       List<EmprestimosDTO> emprestimosDto =  bibliotecaEmprestimoService.getAll();
       return new ResponseEntity<>(emprestimosDto,HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<EmprestimosDTO> addEmprestimo(@RequestBody EmprestimosDTO entity) {
        valiadtionEmprestimoDto.validaEmprestimoDto(entity);
        EmprestimosDTO emprestimoCreated = bibliotecaEmprestimoService.addEmprestimo(entity);
        
        return new ResponseEntity<>(emprestimoCreated,HttpStatus.CREATED);
    }
    
    @PutMapping("/devolver/{emprestimoId}")
    public ResponseEntity<String> devolverLivro(@PathVariable Long emprestimoId) {
        bibliotecaEmprestimoService.devolverLivro(emprestimoId);
        return ResponseEntity.ok("Livro devolvido com sucesso!");
    }
}
