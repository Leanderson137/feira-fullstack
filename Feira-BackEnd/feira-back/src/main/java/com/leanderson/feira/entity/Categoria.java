package com.leanderson.feira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    public Categoria() {
    }

    public Categoria(Long id, String nome, String descricao) {
        this.id = id;

        if (nome != null) {
            this.nome = nome.trim();
        }

        if (descricao != null) {
            this.descricao = descricao.trim();
        }
    }

    public void validar() {
        if (nome == null || nome.length() < 3) {
            throw new IllegalArgumentException("Nome da categoria deve ter ao menos 3 caracteres.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        if (nome != null) {
            this.nome = nome.trim();
        }
    }

    public void setDescricao(String descricao) {
        if (descricao != null) {
            this.descricao = descricao.trim();
        }
    }
}