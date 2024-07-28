package com.biblioteca.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.app.dtos.EmailUserDto;
import com.biblioteca.app.dtos.UsuarioDTO;
import com.biblioteca.app.model.Usuario;
import com.biblioteca.app.repository.UsuarioRepository;


@Service
public class BibliotecaUserServices {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;

    public BibliotecaUserServices(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<UsuarioDTO> getAllUsers(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTO addUser(UsuarioDTO usuarioDTO) {
       
    
        // Converter DTO para entidade
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        
        if (usuario == null) {
            throw new RuntimeException("O usuário é nulo");
        } else {
            // Salvar usuário no banco de dados
            Usuario savedUser = usuarioRepository.save(usuario);
            
            // Atualizar o DTO com o ID gerado pelo banco de dados
            UsuarioDTO savedUserDTO = modelMapper.map(savedUser, UsuarioDTO.class);
        
            return savedUserDTO;
        }
        
        
    }

    public UsuarioDTO findByEmailDto(EmailUserDto email){
            Usuario emailUserIsPresent = usuarioRepository.findByEmail(email.getEmail());
            UsuarioDTO usuarioEncontrado = modelMapper.map(emailUserIsPresent,UsuarioDTO.class);
            return usuarioEncontrado;
        
    }

    public void deleteUsuario(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public boolean findByEmail(String email){
       Usuario emailUserIsPresent = usuarioRepository.findByEmail(email);
        if(emailUserIsPresent!=null){
            return true;
        }else{
          return false;
        }
    }

}
