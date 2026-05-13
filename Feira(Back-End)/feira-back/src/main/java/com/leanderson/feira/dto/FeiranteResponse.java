package com.leanderson.feira.dto;

public class FeiranteResponse {

    private Long id;
    private String nome;
    private String cpf;
    private boolean ativo;
    private CategoriaResponse categoria;

    public FeiranteResponse(Long id, String nome, String cpf, boolean ativo, CategoriaResponse categoria) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.ativo = ativo;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public CategoriaResponse getCategoria() {
        return categoria;
    }
}