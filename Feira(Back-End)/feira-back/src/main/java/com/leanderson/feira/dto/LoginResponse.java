package com.leanderson.feira.dto;

public class LoginResponse {

    private String token;
    private String tipo;
    private String nome;

    public LoginResponse(String token, String nome) {
        this.token = token;
        this.tipo = "Bearer";
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }
}