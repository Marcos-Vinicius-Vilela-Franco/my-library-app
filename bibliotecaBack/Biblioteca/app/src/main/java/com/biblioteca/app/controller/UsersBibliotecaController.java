package com.biblioteca.app.controller;

import com.biblioteca.app.utils.ValidationUsuarioDto;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.biblioteca.app.dtos.EmailUserDto;
import com.biblioteca.app.dtos.UsuarioDTO;
import com.biblioteca.app.services.BibliotecaUserServices;


@RestController
@RequestMapping("/lib/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersBibliotecaController {

    @Autowired
    private ValidationUsuarioDto validationUsuarioDto;
    
    @Autowired
    private BibliotecaUserServices bibliotecaServices;

    @GetMapping("/get")
    public ResponseEntity<List<UsuarioDTO>> getUsuarios(){
        List<UsuarioDTO> usuarios = bibliotecaServices.getAllUsers();
            return ResponseEntity.ok(usuarios);
    }
    @GetMapping("/get/email")
    public ResponseEntity<UsuarioDTO>findByEmail(@RequestBody EmailUserDto email){
        validationUsuarioDto.validaEmail(email.getEmail());
        UsuarioDTO responseUserDto = bibliotecaServices.findByEmailDto(email);
        return new ResponseEntity<>(responseUserDto,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UsuarioDTO> addUser(@RequestBody UsuarioDTO usuarioDTO) {
            validationUsuarioDto.validarUsuarioDTO(usuarioDTO);
            UsuarioDTO savedUserDTO = bibliotecaServices.addUser(usuarioDTO);
            return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
        
    }

    @PutMapping("/update")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        validationUsuarioDto.validarUsuarioDtoToUpdate(usuarioDTO);
        UsuarioDTO savedUserDTO = bibliotecaServices.addUser(usuarioDTO);
        return new ResponseEntity<>(savedUserDTO,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletarUsuario(@RequestBody EmailUserDto email) {
        validationUsuarioDto.validaEmail(email.getEmail());
        bibliotecaServices.deleteUsuario(email.getEmail());
        return new ResponseEntity<>("Usuário deletado!",HttpStatus.OK);
    }
    
    
}
