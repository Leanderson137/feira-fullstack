package com.leanderson.feira.service;

import com.leanderson.feira.dto.FeiranteRequest;
import com.leanderson.feira.dto.FeiranteResponse;
import com.leanderson.feira.entity.Categoria;
import com.leanderson.feira.entity.Feirante;
import com.leanderson.feira.entity.Usuario;
import com.leanderson.feira.exception.RegraNegocioException;
import com.leanderson.feira.repository.CategoriaRepository;
import com.leanderson.feira.repository.FeiranteRepository;
import com.leanderson.feira.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FeiranteServiceTest {

    @Mock
    private FeiranteRepository feiranteRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private FeiranteService feiranteService;

    @AfterEach
    void limparContexto() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveCriarFeiranteComSucesso() {

        String email = "teste@email.com";

        autenticarUsuario(email);

        Usuario usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn(email);

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                usuario
        );

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(feiranteRepository.existsByCpfAndUsuarioEmail("12345678901", email))
                .thenReturn(false);

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));

        when(feiranteRepository.save(any(Feirante.class)))
                .thenAnswer(invocation -> {
                    Feirante feirante = invocation.getArgument(0);
                    feirante.setId(1L);
                    return feirante;
                });

        FeiranteRequest request = new FeiranteRequest();
        request.setNome("João Silva");
        request.setCpf("12345678901");
        request.setAtivo(true);
        request.setCategoriaId(1L);

        FeiranteResponse response = feiranteService.criar(request);

        assertEquals(1L, response.getId());
        assertEquals("João Silva", response.getNome());
        assertEquals("12345678901", response.getCpf());
        assertTrue(response.isAtivo());
        assertEquals("Verduras", response.getCategoria().getNome());

        verify(feiranteRepository).save(any(Feirante.class));
    }

    @Test
    void deveLancarErroQuandoCpfJaExistirParaUsuario() {

        String email = "teste@email.com";

        autenticarUsuario(email);

        Usuario usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn(email);

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(feiranteRepository.existsByCpfAndUsuarioEmail("12345678901", email))
                .thenReturn(true);

        FeiranteRequest request = new FeiranteRequest();
        request.setNome("João Silva");
        request.setCpf("12345678901");
        request.setAtivo(true);
        request.setCategoriaId(1L);

        assertThrows(
                RegraNegocioException.class,
                () -> feiranteService.criar(request)
        );

        verify(feiranteRepository, never()).save(any(Feirante.class));
    }

    @Test
    void deveLancarErroQuandoCategoriaNaoPertencerAoUsuario() {

        String emailUsuarioLogado = "usuario1@email.com";
        String emailOutroUsuario = "usuario2@email.com";

        autenticarUsuario(emailUsuarioLogado);

        Usuario usuarioLogado = mock(Usuario.class);
        Usuario outroUsuario = mock(Usuario.class);

        when(usuarioLogado.getEmail()).thenReturn(emailUsuarioLogado);
        when(outroUsuario.getEmail()).thenReturn(emailOutroUsuario);

        Categoria categoriaDeOutroUsuario = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                outroUsuario
        );

        when(usuarioRepository.findByEmail(emailUsuarioLogado))
                .thenReturn(Optional.of(usuarioLogado));

        when(feiranteRepository.existsByCpfAndUsuarioEmail("12345678901", emailUsuarioLogado))
                .thenReturn(false);

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoriaDeOutroUsuario));

        FeiranteRequest request = new FeiranteRequest();
        request.setNome("João Silva");
        request.setCpf("12345678901");
        request.setAtivo(true);
        request.setCategoriaId(1L);

        assertThrows(
                RegraNegocioException.class,
                () -> feiranteService.criar(request)
        );

        verify(feiranteRepository, never()).save(any(Feirante.class));
    }

    @Test
    void deveListarFeirantesDoUsuarioLogado() {

        String email = "teste@email.com";

        autenticarUsuario(email);

        Usuario usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn(email);

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                usuario
        );

        Feirante feirante1 = new Feirante(
                1L,
                "João Silva",
                "12345678901",
                true,
                categoria,
                usuario
        );

        Feirante feirante2 = new Feirante(
                2L,
                "Maria Souza",
                "98765432100",
                false,
                categoria,
                usuario
        );

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(feiranteRepository.findByUsuarioEmail(email))
                .thenReturn(List.of(feirante1, feirante2));

        List<FeiranteResponse> feirantes = feiranteService.listarTodos();

        assertEquals(2, feirantes.size());
        assertEquals("João Silva", feirantes.get(0).getNome());
        assertEquals("Maria Souza", feirantes.get(1).getNome());
        assertEquals("Verduras", feirantes.get(0).getCategoria().getNome());
    }

    @Test
    void deveRemoverFeiranteDoUsuarioLogado() {

        String email = "teste@email.com";

        autenticarUsuario(email);

        Usuario usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn(email);

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                usuario
        );

        Feirante feirante = new Feirante(
                1L,
                "João Silva",
                "12345678901",
                true,
                categoria,
                usuario
        );

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(feiranteRepository.findById(1L))
                .thenReturn(Optional.of(feirante));

        feiranteService.remover(1L);

        verify(feiranteRepository).delete(feirante);
    }

    @Test
    void deveLancarErroAoRemoverFeiranteDeOutroUsuario() {

        String emailUsuarioLogado = "usuario1@email.com";
        String emailOutroUsuario = "usuario2@email.com";

        autenticarUsuario(emailUsuarioLogado);

        Usuario usuarioLogado = mock(Usuario.class);
        Usuario outroUsuario = mock(Usuario.class);

        when(usuarioLogado.getEmail()).thenReturn(emailUsuarioLogado);
        when(outroUsuario.getEmail()).thenReturn(emailOutroUsuario);

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                outroUsuario
        );

        Feirante feiranteDeOutroUsuario = new Feirante(
                1L,
                "João Silva",
                "12345678901",
                true,
                categoria,
                outroUsuario
        );

        when(usuarioRepository.findByEmail(emailUsuarioLogado))
                .thenReturn(Optional.of(usuarioLogado));

        when(feiranteRepository.findById(1L))
                .thenReturn(Optional.of(feiranteDeOutroUsuario));

        assertThrows(
                RegraNegocioException.class,
                () -> feiranteService.remover(1L)
        );

        verify(feiranteRepository, never()).delete(any(Feirante.class));
    }

    private void autenticarUsuario(String email) {

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, null);

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }
}