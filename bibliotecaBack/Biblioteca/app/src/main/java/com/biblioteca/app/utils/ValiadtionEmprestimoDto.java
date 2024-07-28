package com.biblioteca.app.utils;

import org.springframework.stereotype.Component;

import com.biblioteca.app.dtos.EmprestimosDTO;
import com.biblioteca.app.excecoes.BadRequestLivroDto;
import com.biblioteca.app.excecoes.BadRequestUsuarioDto;

@Component
public class ValiadtionEmprestimoDto {
    public void validaEmprestimoDto(EmprestimosDTO emprestimosDTO){
        if (emprestimosDTO.getUsuarioEmail() == null || emprestimosDTO.getUsuarioEmail().isEmpty()) {
            throw new BadRequestUsuarioDto("O campo 'email' é obrigatório");
        }
        // if (emprestimosDTO.getNome_livro()== null || emprestimosDTO.getNome_livro().toString().isEmpty()) {
        //     throw new BadRequestLivroDto("O campo 'nome do livro' é obrigatório");
        // }
        if (emprestimosDTO.getLivroId()== null || emprestimosDTO.getLivroId().toString().isEmpty()) {
            throw new BadRequestLivroDto("O campo 'livro' é obrigatório");
        }
        if (!isValidEmail(emprestimosDTO.getUsuarioEmail())) {
            throw new BadRequestUsuarioDto("O 'email' fornecido não é válido");
        }
    }
    public boolean isValidEmail(String email) {
        // Expressão regular para validar formato de email
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }
}
