package com.leanderson.feira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FeiranteRequest {

    @NotBlank(message = "Nome do feirante é obrigatório.")
    @Size(min = 3, message = "Nome do feirante deve ter pelo menos 3 caracteres.")
    private String nome;

    @NotBlank(message = "CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "CPF inválido.")
    private String cpf;

    private boolean ativo;

    @NotNull(message = "Categoria é obrigatória.")
    private Long categoriaId;

    public FeiranteRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}