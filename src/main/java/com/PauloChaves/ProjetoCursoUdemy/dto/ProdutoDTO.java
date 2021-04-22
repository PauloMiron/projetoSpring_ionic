package com.PauloChaves.ProjetoCursoUdemy.dto;

import com.PauloChaves.ProjetoCursoUdemy.entities.Produto;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private Long id;
    private String nome;
    private Double preco;

    public ProdutoDTO(){}

    public ProdutoDTO(Produto obj){
        id = obj.getId();
        nome = obj.getNome();
        preco = obj.getPreco();
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
