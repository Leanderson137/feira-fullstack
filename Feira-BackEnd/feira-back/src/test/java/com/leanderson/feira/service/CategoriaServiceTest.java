package com.leanderson.feira.service;

import com.leanderson.feira.dto.CategoriaRequest;
import com.leanderson.feira.dto.CategoriaResponse;
import com.leanderson.feira.entity.Categoria;
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

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private FeiranteRepository feiranteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @AfterEach
    void limparContexto() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveCriarCategoriaComSucesso() {

        String email = "teste@email.com";

        autenticarUsuario(email);

        Usuario usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn(email);

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(categoriaRepository.existsByNomeAndUsuarioEmail("Verduras", email))
                .thenReturn(false);

        when(categoriaRepository.save(any(Categoria.class)))
                .thenAnswer(invocation -> {
                    Categoria categoria = invocation.getArgument(0);
                    categoria.setId(1L);
                    return categoria;
                });

        CategoriaRequest request = new CategoriaRequest();
        request.setNome("Verduras");
        request.setDescricao("Produtos verdes e hortaliças");

        CategoriaResponse response = categoriaService.criar(request);

        assertEquals(1L, response.getId());
        assertEquals("Verduras", response.getNome());
        assertEquals("Produtos verdes e hortaliças", response.getDescricao());

        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void deveLancarErroQuandoCategoriaJaExistirParaUsuario() {

        String email = "teste@email.com";

        autenticarUsuario(email);

        Usuario usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn(email);

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(categoriaRepository.existsByNomeAndUsuarioEmail("Verduras", email))
                .thenReturn(true);

        CategoriaRequest request = new CategoriaRequest();
        request.setNome("Verduras");
        request.setDescricao("Produtos verdes e hortaliças");

        assertThrows(
                RegraNegocioException.class,
                () -> categoriaService.criar(request)
        );

        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void deveListarCategoriasDoUsuarioLogado() {

        String email = "teste@email.com";

        autenticarUsuario(email);

        Usuario usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn(email);

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        Categoria categoria1 = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                usuario
        );

        Categoria categoria2 = new Categoria(
                2L,
                "Frutas",
                "Frutas frescas",
                usuario
        );

        when(categoriaRepository.findByUsuarioEmail(email))
                .thenReturn(List.of(categoria1, categoria2));

        List<CategoriaResponse> categorias = categoriaService.listarTodas();

        assertEquals(2, categorias.size());
        assertEquals("Verduras", categorias.get(0).getNome());
        assertEquals("Frutas", categorias.get(1).getNome());
    }

    @Test
    void deveRemoverCategoriaQuandoNaoEstiverEmUso() {

        String email = "teste@email.com";

        autenticarUsuario(email);

        Usuario usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn(email);

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                usuario
        );

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));

        when(feiranteRepository.existsByCategoriaId(1L))
                .thenReturn(false);

        categoriaService.remover(1L);

        verify(categoriaRepository).delete(categoria);
    }

    @Test
    void deveLancarErroAoRemoverCategoriaEmUso() {

        String email = "teste@email.com";

        autenticarUsuario(email);

        Usuario usuario = mock(Usuario.class);

        when(usuario.getEmail()).thenReturn(email);

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        Categoria categoria = new Categoria(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças",
                usuario
        );

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));

        when(feiranteRepository.existsByCategoriaId(1L))
                .thenReturn(true);

        assertThrows(
                RegraNegocioException.class,
                () -> categoriaService.remover(1L)
        );

        verify(categoriaRepository, never()).delete(any(Categoria.class));
    }

    private void autenticarUsuario(String email) {

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, null);

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
    }
}