package com.biblioteca.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Usuario {
    
    
    @Id
    private String email;

    private String nome;
    

}
