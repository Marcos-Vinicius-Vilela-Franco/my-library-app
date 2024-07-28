package com.biblioteca.app.utils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biblioteca.app.dtos.LivroDTO;
import com.biblioteca.app.dtos.NomeAutorDto;
import com.biblioteca.app.excecoes.BadRequestLivroDto;
import com.biblioteca.app.excecoes.NotFoundLivroDto;
import com.biblioteca.app.repository.LivroRopository;

@Component
public class ValidationLivroDto {
    
    @Autowired
    private LivroRopository livroRopository;

    public void MensageErroDelete (){
        throw new BadRequestLivroDto("Não é possível excluir o livro. Existem empréstimos associados a este livro.");
    }
    public void validarLivroDTO(LivroDTO livroDTO) {
        if (livroDTO.getAutor() == null || livroDTO.getAutor().isEmpty()) {
            throw new BadRequestLivroDto("O campo 'autor' é obrigatório");
        }

        if (livroDTO.getTitulo() == null || livroDTO.getTitulo().isEmpty()) {
            throw new BadRequestLivroDto("O campo 'título' é obrigatório");
        }
          
    }
    public void validarUsuarioDtoToUpdate(LivroDTO livroDTO) {
        if (livroDTO.getId() == null) {
            throw new NotFoundLivroDto("O campo 'id' é obrigatório");
        }

        if (livroDTO.getAutor() == null || livroDTO.getAutor().isEmpty()) {
            throw new BadRequestLivroDto("O campo 'autor' é obrigatório");
        }

        if (livroDTO.getTitulo() == null || livroDTO.getTitulo().isEmpty()) {
            throw new BadRequestLivroDto("O campo 'título' é obrigatório");
        }
          
    }
    public void validaAutor(NomeAutorDto autor) {
        if (autor.getAutor() == null || autor.getAutor().isEmpty()) {
            throw new BadRequestLivroDto("O campo 'autor' é obrigatório");
        }
    }
    
    public void existsByAutor(String autor){
        if (!livroRopository.existsByAutor(autor)) {
            throw new NotFoundLivroDto("Usuário não encontrado!");
        } 
    }

    public void existsById(Long id){
        if(id==null){
            throw new NotFoundLivroDto("Insira um ID válido!");
        }
        if (!livroRopository.existsById(id)) {
            throw new NotFoundLivroDto("Livro não encontrado!");
        } 
    }

}
