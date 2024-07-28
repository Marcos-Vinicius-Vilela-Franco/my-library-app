package com.biblioteca.app.dtos;

import java.util.Date;

import com.biblioteca.app.enums.StatusEmprestimo;

import lombok.Data;

@Data
public class EmprestimosDTO {
    private Long id;

    private String nome_livro;

    private Long livroId;

    private String usuarioEmail;

    private Date dataEmprestimo;

    private Date dataDevolucaoReal;

    private StatusEmprestimo statusEmprestimo;

}
