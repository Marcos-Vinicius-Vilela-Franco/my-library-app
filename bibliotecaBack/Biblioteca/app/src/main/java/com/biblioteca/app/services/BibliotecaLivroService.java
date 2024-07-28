package com.biblioteca.app.services;

import com.biblioteca.app.dtos.LivroDTO;
import com.biblioteca.app.excecoes.NotFoundLivroDto;
import com.biblioteca.app.model.Livro;
import com.biblioteca.app.repository.LivroRopository;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BibliotecaLivroService {

    @Autowired
    private LivroRopository livroRepository;

    private final ModelMapper modelMapper;

    public BibliotecaLivroService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LivroDTO> getLivroByAutor(String autor){
        List<Livro> livros = livroRepository.findByAutor(autor);
        return livros.stream()
                .map(livro -> modelMapper.map(livro, LivroDTO.class))
                .collect(Collectors.toList());

    }

    public List<LivroDTO> getAllLivros(){
        List<Livro> livros = livroRepository.findAll();

        return livros.stream()
                .map(livro -> modelMapper.map(livro, LivroDTO.class))
                .collect(Collectors.toList());
    }

    public LivroDTO addLivro(LivroDTO livro) {
        if (livro.getEmprestado() == null) {
            livro.setEmprestado(false);
        }
        // Converter DTO para entidade
        Livro livroConvertido = modelMapper.map(livro, Livro.class);

        if (livroConvertido == null) {
            throw new RuntimeException("Erro interno, o usuário é nulo");
        } else {
            Livro novoLivro = livroRepository.save(livroConvertido);
            LivroDTO novoLivroDto = modelMapper.map(novoLivro, LivroDTO.class);
            return novoLivroDto;
        }
    }

    public LivroDTO updateLivro(LivroDTO livroDTO){
        if (!livroRepository.existsById(livroDTO.getId())) {
           throw new NotFoundLivroDto("Livro não encontrado");
        }else{
            Livro livroConvertido = modelMapper.map(livroDTO, Livro.class);
            Livro updatedLivro = livroRepository.save(livroConvertido);
            LivroDTO livroAtualizadoDto = modelMapper.map(updatedLivro,LivroDTO.class);
            return livroAtualizadoDto;
        }
    }

    public void deleteLivro(Long id){
        livroRepository.deleteById(id);
    }
    

}