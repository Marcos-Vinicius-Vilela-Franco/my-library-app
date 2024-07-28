package com.biblioteca.app.utils;

import com.biblioteca.app.excecoes.BadRequestUsuarioDto;
import com.biblioteca.app.excecoes.NotFoundRequestUsuarioDTO;
import com.biblioteca.app.services.BibliotecaUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.biblioteca.app.dtos.UsuarioDTO;

@Component
public class ValidationUsuarioDto {
 

    @Autowired
    public BibliotecaUserServices bibliotecaServices;

    public void validarUsuarioDTO(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getNome() == null || usuarioDTO.getNome().isEmpty()) {
            throw new BadRequestUsuarioDto("O campo 'nome' é obrigatório");
        }

        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            throw new BadRequestUsuarioDto("O campo 'email' é obrigatório");
        }

        if (!isValidEmail(usuarioDTO.getEmail())) {
            throw new BadRequestUsuarioDto("O 'email' fornecido não é válido");
        }
        if(bibliotecaServices.findByEmail(usuarioDTO.getEmail())){
            throw new BadRequestUsuarioDto("O 'email' já está cadastrado!");
        }
    }


    public void validarUsuarioDtoToUpdate(UsuarioDTO usuarioDTO){
        if (usuarioDTO.getNome() == null || usuarioDTO.getNome().isEmpty()) {
            throw new BadRequestUsuarioDto("O campo 'nome' é obrigatório");
        }

        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            throw new BadRequestUsuarioDto("O campo 'email' é obrigatório");
        }
        if(!bibliotecaServices.findByEmail(usuarioDTO.getEmail())){
            throw new NotFoundRequestUsuarioDTO("O 'email' não está cadastrado!");
        }
    }
    public void validaEmail(String email){
        
        if(!isValidEmail(email)){
            throw new BadRequestUsuarioDto("O 'email' fornecido não é válido ");
        }
        if(!bibliotecaServices.findByEmail(email)){
            throw new NotFoundRequestUsuarioDTO("O 'email' fornecido não está cadastrado");
        }
        
    }


    public boolean isValidEmail(String email) {
        // Expressão regular para validar formato de email
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }
}