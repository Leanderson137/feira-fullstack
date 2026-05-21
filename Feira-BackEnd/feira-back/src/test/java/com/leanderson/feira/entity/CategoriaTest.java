package com.leanderson.feira.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoriaTest {

    @Test
    void deveValidarCategoriaComDadosValidos() {

        Categoria categoria = new Categoria(
                null,
                "Verduras",
                "Produtos verdes e hortaliças",
                null
        );

        assertDoesNotThrow(categoria::validar);
    }

    @Test
    void deveLancarErroQuandoNomeForMenorQueTresCaracteres() {

        Categoria categoria = new Categoria(
                null,
                "AB",
                "Descrição válida",
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                categoria::validar
        );
    }

    @Test
    void deveLancarErroQuandoDescricaoEstiverVazia() {

        Categoria categoria = new Categoria(
                null,
                "Frutas",
                "",
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                categoria::validar
        );
    }
}