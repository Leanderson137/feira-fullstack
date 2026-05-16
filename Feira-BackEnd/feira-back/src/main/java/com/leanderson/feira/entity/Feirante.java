package com.leanderson.feira.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Feirante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Feirante() {

    }

    public Feirante(
            Long id,
            String nome,
            String cpf,
            boolean ativo,
            Categoria categoria,
            Usuario usuario
    ) {
        this.id = id;

        if (nome != null) {
            this.nome = nome.trim();
        }

        if (cpf != null) {
            this.cpf = cpf.trim();
        }

        this.ativo = ativo;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    public void validar() {

        if (nome == null || nome.length() < 3) {
            throw new IllegalArgumentException(
                    "Nome do feirante deve ter ao menos 3 caracteres."
            );
        }

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException(
                    "CPF do feirante e obrigatorio."
            );
        }

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException(
                    "CPF deve ter exatamente 11 digitos numericos."
            );
        }

        if (categoria == null) {
            throw new IllegalArgumentException(
                    "Categoria do feirante e obrigatoria."
            );
        }
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

        if (nome != null) {
            this.nome = nome.trim();
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {

        if (cpf != null) {
            this.cpf = cpf.trim();
        }
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}