package com.leanderson.feira.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsuarioTest {

    @Test
    void deveValidarUsuarioComDadosValidos() {

        Usuario usuario = new Usuario(
                null,
                "Leanderson Lima",
                "teste@email.com",
                "12345678"
        );

        assertDoesNotThrow(usuario::validar);
    }

    @Test
    void deveLancarErroQuandoNomeForMenorQueTresCaracteres() {

        Usuario usuario = new Usuario(
                null,
                "Le",
                "teste@email.com",
                "12345678"
        );

        assertThrows(
                IllegalArgumentException.class,
                usuario::validar
        );
    }

    @Test
    void deveLancarErroQuandoEmailEstiverVazio() {

        Usuario usuario = new Usuario(
                null,
                "Leanderson Lima",
                "",
                "12345678"
        );

        assertThrows(
                IllegalArgumentException.class,
                usuario::validar
        );
    }

    @Test
    void deveLancarErroQuandoSenhaForMenorQueOitoCaracteres() {

        Usuario usuario = new Usuario(
                null,
                "Leanderson Lima",
                "teste@email.com",
                "1234567"
        );

        assertThrows(
                IllegalArgumentException.class,
                usuario::validar
        );
    }
}