package com.biblioteca.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.app.model.Emprestimos;

@Repository
public interface EmprestimosRepository extends JpaRepository<Emprestimos,Long>{
    
}
