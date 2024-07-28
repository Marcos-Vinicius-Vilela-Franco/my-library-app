package com.biblioteca.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.biblioteca.app.model.Livro;

import org.springframework.lang.NonNull;

@Repository
public interface LivroRopository extends JpaRepository<Livro,Long>{
    List<Livro> findByAutor(String autor);
    boolean existsByAutor(String autor);
    boolean existsById(@NonNull Long id);

    @Query("SELECT l FROM Livro l WHERE l.emprestado = TRUE")
    List<Livro> findByEmprestadoIsTrue();

    @Query("SELECT l FROM Livro l WHERE l.emprestado = FALSE")
    List<Livro> findByEmprestadoIsFalse();
}
