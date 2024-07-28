package com.biblioteca.app.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.app.dtos.EmprestimosDTO;
import com.biblioteca.app.enums.StatusEmprestimo;
import com.biblioteca.app.excecoes.BadRequestLivroDto;
import com.biblioteca.app.excecoes.NotFoundLivroDto;
import com.biblioteca.app.excecoes.NotFoundRequestUsuarioDTO;
import com.biblioteca.app.model.Emprestimos;
import com.biblioteca.app.model.Livro;
import com.biblioteca.app.model.Usuario;
import com.biblioteca.app.repository.EmprestimosRepository;
import com.biblioteca.app.repository.LivroRopository;
import com.biblioteca.app.repository.UsuarioRepository;

@Service
public class BibliotecaEmprestimoService {

    private final ModelMapper modelMapper;

    public BibliotecaEmprestimoService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    private EmprestimosRepository emprestimosRepository;

    @Autowired
    private LivroRopository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EmprestimosDTO addEmprestimo(EmprestimosDTO emprestimosDTO) {

        if (!findByEmail(emprestimosDTO.getUsuarioEmail())) {
            throw new NotFoundRequestUsuarioDTO("O 'email' não está cadastrado!");
        }
    
        Optional<Livro> livroOptional = livroRepository.findById(emprestimosDTO.getLivroId());
        
    
        if (livroOptional.isPresent()) {
            Livro livro = livroOptional.get();
    
            if (!livro.getEmprestado()) {
                livro.setEmprestado(true);
                String nomeLivro = livro.getTitulo();

                livroRepository.save(livro);
    
                emprestimosDTO.setNome_livro(nomeLivro);
                emprestimosDTO.setDataEmprestimo(new Date());
                emprestimosDTO.setStatusEmprestimo(StatusEmprestimo.EM_ANDAMENTO);
    
                Emprestimos emprestimoConvertido = modelMapper.map(emprestimosDTO, Emprestimos.class);
    
                Emprestimos emprestimoSalvo = emprestimosRepository.save(emprestimoConvertido);
    
                EmprestimosDTO emprestimoDtoCreated = modelMapper.map(emprestimoSalvo, EmprestimosDTO.class);
    
                return emprestimoDtoCreated;
            } else {
                throw new BadRequestLivroDto("O livro já está emprestado!");
            }
        } else {
            throw new NotFoundLivroDto("O 'id' do livro não existe!");
        }
    }


    public void devolverLivro(Long emprestimoId) {
        Optional<Emprestimos> emprestimoOptional = emprestimosRepository.findById(emprestimoId);

        if (emprestimoOptional.isPresent()) {
            Emprestimos emprestimo = emprestimoOptional.get();

            if (emprestimo.getStatusEmprestimo() == StatusEmprestimo.EM_ANDAMENTO) {
                emprestimo.getLivro().setEmprestado(false); // Marcar o livro como disponível
                emprestimo.setDataDevolucaoReal(new Date()); // Registrar a data de devolução real
                emprestimo.setStatusEmprestimo(StatusEmprestimo.DEVOLVIDO); // Atualizar o status do empréstimo

                livroRepository.save(emprestimo.getLivro()); // Salvar as alterações no livro
                emprestimosRepository.save(emprestimo); // Salvar as alterações no empréstimo
            } else {
                throw new BadRequestLivroDto("O livro já está disponível ou o empréstimo foi cancelado/perdido!");
            }
        } else {
            throw new NotFoundLivroDto("Empréstimo não encontrado!");
        }
    }
    
    public boolean findByEmail(String email) {
        Usuario emailUserIsPresent = usuarioRepository.findByEmail(email);
        if (emailUserIsPresent != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<EmprestimosDTO> getAll() {
        List<Emprestimos> emprestimos = emprestimosRepository.findAll();
        return emprestimos.stream()
                .map(emprestimo -> modelMapper.map(emprestimo, EmprestimosDTO.class))
                .collect(Collectors.toList());
    }

}
