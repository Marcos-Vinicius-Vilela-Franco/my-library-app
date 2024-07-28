package com.biblioteca.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.biblioteca.app.model.Usuario;

import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String>{
    Usuario findByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM Usuario u WHERE u.email = ?1")
    void deleteByEmail(String email);
}
