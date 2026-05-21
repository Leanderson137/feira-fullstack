package com.leanderson.feira.repository;

import com.leanderson.feira.entity.Categoria;
import com.leanderson.feira.entity.Feirante;
import com.leanderson.feira.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FeiranteRepositoryTest {

    @Autowired
    private FeiranteRepository feiranteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveBuscarFeirantesPorEmailDoUsuario() {

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

        Feirante feirante = new Feirante(
                null,
                "João Silva",
                "12345678901",
                true,
                categoria,
                usuario
        );

        feiranteRepository.save(feirante);

        List<Feirante> feirantes =
                feiranteRepository.findByUsuarioEmail("teste@email.com");

        assertEquals(1, feirantes.size());
        assertEquals("João Silva", feirantes.get(0).getNome());
        assertEquals("teste@email.com", feirantes.get(0).getUsuario().getEmail());
    }

    @Test
    void deveVerificarSeCpfExisteParaUsuario() {

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

        Feirante feirante = new Feirante(
                null,
                "João Silva",
                "12345678901",
                true,
                categoria,
                usuario
        );

        feiranteRepository.save(feirante);

        boolean existe =
                feiranteRepository.existsByCpfAndUsuarioEmail(
                        "12345678901",
                        "teste@email.com"
                );

        assertTrue(existe);
    }

    @Test
    void deveVerificarSeCategoriaEstaEmUso() {

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

        Feirante feirante = new Feirante(
                null,
                "João Silva",
                "12345678901",
                true,
                categoria,
                usuario
        );

        feiranteRepository.save(feirante);

        boolean existe =
                feiranteRepository.existsByCategoriaId(categoria.getId());

        assertTrue(existe);
    }

    @Test
    void deveNaoEncontrarFeiranteDeOutroUsuario() {

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

        Feirante feirante = new Feirante(
                null,
                "João Silva",
                "12345678901",
                true,
                categoria,
                usuario1
        );

        feiranteRepository.save(feirante);

        List<Feirante> feirantesUsuario2 =
                feiranteRepository.findByUsuarioEmail("usuario2@email.com");

        assertTrue(feirantesUsuario2.isEmpty());
    }
}