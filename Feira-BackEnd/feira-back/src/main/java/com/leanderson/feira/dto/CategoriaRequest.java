package com.leanderson.feira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaRequest {

    @NotBlank(message = "Nome da categoria é obrigatório.")
    @Size(min = 3, message = "Nome da categoria deve ter pelo menos 3 caracteres.")
    private String nome;

    @NotBlank(message = "Descrição da categoria é obrigatória.")
    private String descricao;

    public CategoriaRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}