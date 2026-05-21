package com.leanderson.feira.repository;

import com.leanderson.feira.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveBuscarUsuarioPorEmail() {

        Usuario usuario = new Usuario(
                null,
                "Leanderson Lima",
                "teste@email.com",
                "12345678"
        );

        usuarioRepository.save(usuario);

        Optional<Usuario> resultado =
                usuarioRepository.findByEmail("teste@email.com");

        assertTrue(resultado.isPresent());
        assertEquals("Leanderson Lima", resultado.get().getNome());
        assertEquals("teste@email.com", resultado.get().getEmail());
    }

    @Test
    void deveVerificarSeEmailExiste() {

        Usuario usuario = new Usuario(
                null,
                "Leanderson Lima",
                "teste@email.com",
                "12345678"
        );

        usuarioRepository.save(usuario);

        boolean existe =
                usuarioRepository.existsByEmail("teste@email.com");

        assertTrue(existe);
    }

    @Test
    void deveRetornarFalsoQuandoEmailNaoExistir() {

        boolean existe =
                usuarioRepository.existsByEmail("naoexiste@email.com");

        assertFalse(existe);
    }
}