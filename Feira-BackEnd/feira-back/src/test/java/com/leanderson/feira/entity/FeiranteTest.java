package com.leanderson.feira.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FeiranteTest {

    @Test
    void deveValidarFeiranteComDadosValidos() {

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                null
        );

        Feirante feirante = new Feirante(
                null,
                "João Silva",
                "12345678901",
                true,
                categoria,
                null
        );

        assertDoesNotThrow(feirante::validar);
    }

    @Test
    void deveLancarErroQuandoNomeForMenorQueTresCaracteres() {

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                null
        );

        Feirante feirante = new Feirante(
                null,
                "AB",
                "12345678901",
                true,
                categoria,
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                feirante::validar
        );
    }

    @Test
    void deveLancarErroQuandoCpfEstiverVazio() {

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                null
        );

        Feirante feirante = new Feirante(
                null,
                "João Silva",
                "",
                true,
                categoria,
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                feirante::validar
        );
    }

    @Test
    void deveLancarErroQuandoCpfNaoTiverOnzeDigitos() {

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                null
        );

        Feirante feirante = new Feirante(
                null,
                "João Silva",
                "123",
                true,
                categoria,
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                feirante::validar
        );
    }

    @Test
    void deveLancarErroQuandoCategoriaForNula() {

        Feirante feirante = new Feirante(
                null,
                "João Silva",
                "12345678901",
                true,
                null,
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                feirante::validar
        );
    }
}