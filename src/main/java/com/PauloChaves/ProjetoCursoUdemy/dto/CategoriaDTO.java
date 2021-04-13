package com.PauloChaves.ProjetoCursoUdemy.dto;

import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;

import java.io.Serializable;

public class CategoriaDTO implements Serializable {

    private Long id;
    private String nome;

    public CategoriaDTO(){}

    public CategoriaDTO(Categoria obj){
        id = obj.getId();
        nome = obj.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
