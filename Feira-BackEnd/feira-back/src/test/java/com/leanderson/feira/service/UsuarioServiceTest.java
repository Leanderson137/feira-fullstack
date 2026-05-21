package com.leanderson.feira.service;

import com.leanderson.feira.dto.UsuarioResponse;
import com.leanderson.feira.entity.Usuario;
import com.leanderson.feira.exception.RegraNegocioException;
import com.leanderson.feira.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveListarTodosOsUsuarios() {

        Usuario usuario1 = new Usuario(
                1L,
                "Leanderson Lima",
                "leanderson@email.com",
                "12345678"
        );

        Usuario usuario2 = new Usuario(
                2L,
                "Maria Silva",
                "maria@email.com",
                "12345678"
        );

        when(usuarioRepository.findAll())
                .thenReturn(List.of(usuario1, usuario2));

        List<UsuarioResponse> usuarios = usuarioService.listarTodos();

        assertEquals(2, usuarios.size());

        assertEquals(1L, usuarios.get(0).getId());
        assertEquals("Leanderson Lima", usuarios.get(0).getNome());
        assertEquals("leanderson@email.com", usuarios.get(0).getEmail());

        assertEquals(2L, usuarios.get(1).getId());
        assertEquals("Maria Silva", usuarios.get(1).getNome());
        assertEquals("maria@email.com", usuarios.get(1).getEmail());
    }

    @Test
    void deveRemoverUsuarioQuandoExistir() {

        Usuario usuario = new Usuario(
                1L,
                "Leanderson Lima",
                "leanderson@email.com",
                "12345678"
        );

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        usuarioService.remover(1L);

        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void deveLancarErroQuandoUsuarioNaoExistirAoRemover() {

        when(usuarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                RegraNegocioException.class,
                () -> usuarioService.remover(99L)
        );

        verify(usuarioRepository, never()).delete(any(Usuario.class));
    }
}