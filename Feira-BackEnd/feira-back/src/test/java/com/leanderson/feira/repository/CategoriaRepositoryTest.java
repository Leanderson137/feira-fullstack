package com.leanderson.feira.repository;

import com.leanderson.feira.entity.Categoria;
import com.leanderson.feira.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveBuscarCategoriasPorEmailDoUsuario() {

        Usuario usuario = new Usuario(
                null,
                "Leanderson Lima",
                "teste@email.com",
                "12345678"
        );

        usuarioRepository.save(usuario);

        Categoria categoria = new Categoria(
                null,
                "Verduras",
                "Produtos verdes e hortaliças",
                usuario
        );

        categoriaRepository.save(categoria);

        List<Categoria> categorias =
                categoriaRepository.findByUsuarioEmail("teste@email.com");

        assertEquals(1, categorias.size());
        assertEquals("Verduras", categorias.get(0).getNome());
        assertEquals("teste@email.com", categorias.get(0).getUsuario().getEmail());
    }

    @Test
    void deveVerificarSeCategoriaExistePorNomeEEmailDoUsuario() {

        Usuario usuario = new Usuario(
                null,
                "Leanderson Lima",
                "teste@email.com",
                "12345678"
        );

        usuarioRepository.save(usuario);

        Categoria categoria = new Categoria(
                null,
                "Frutas",
                "Frutas frescas",
                usuario
        );

        categoriaRepository.save(categoria);

        boolean existe =
                categoriaRepository.existsByNomeAndUsuarioEmail(
                        "Frutas",
                        "teste@email.com"
                );

        assertTrue(existe);
    }

    @Test
    void deveNaoEncontrarCategoriaDeOutroUsuario() {

        Usuario usuario1 = new Usuario(
                null,
                "Usuário Um",
                "usuario1@email.com",
                "12345678"
        );

        Usuario usuario2 = new Usuario(
                null,
                "Usuário Dois",
                "usuario2@email.com",
                "12345678"
        );

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);

        Categoria categoria = new Categoria(
                null,
                "Verduras",
                "Produtos verdes e hortaliças",
                usuario1
        );

        categoriaRepository.save(categoria);

        List<Categoria> categoriasUsuario2 =
                categoriaRepository.findByUsuarioEmail("usuario2@email.com");

        assertTrue(categoriasUsuario2.isEmpty());
    }
}