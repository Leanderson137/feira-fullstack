package com.leanderson.feira.service;

import com.leanderson.feira.dto.LoginRequest;
import com.leanderson.feira.dto.LoginResponse;
import com.leanderson.feira.dto.UsuarioRequest;
import com.leanderson.feira.entity.Usuario;
import com.leanderson.feira.exception.RegraNegocioException;
import com.leanderson.feira.repository.UsuarioRepository;
import com.leanderson.feira.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void deveCadastrarUsuarioComSucesso() {

        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Leanderson Lima");
        request.setEmail("leanderson@email.com");
        request.setSenha("12345678");

        when(usuarioRepository.existsByEmail("leanderson@email.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("12345678"))
                .thenReturn("senha-criptografada");

        authService.cadastrar(request);

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deveLancarErroQuandoEmailJaEstiverCadastrado() {

        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Leanderson Lima");
        request.setEmail("leanderson@email.com");
        request.setSenha("12345678");

        when(usuarioRepository.existsByEmail("leanderson@email.com"))
                .thenReturn(true);

        assertThrows(
                RegraNegocioException.class,
                () -> authService.cadastrar(request)
        );

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void deveRealizarLoginComSucesso() {

        LoginRequest request = new LoginRequest();
        request.setEmail("leanderson@email.com");
        request.setSenha("12345678");

        Usuario usuario = new Usuario(
                1L,
                "Leanderson Lima",
                "leanderson@email.com",
                "senha-criptografada"
        );

        when(usuarioRepository.findByEmail("leanderson@email.com"))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches("12345678", "senha-criptografada"))
                .thenReturn(true);

        when(jwtService.gerarToken("leanderson@email.com"))
                .thenReturn("token-jwt");

        LoginResponse response = authService.login(request);

        assertEquals("token-jwt", response.getToken());
        assertEquals("Leanderson Lima", response.getNome());
        assertEquals("Bearer", response.getTipo());
    }

    @Test
    void deveLancarErroQuandoEmailNaoExistirNoLogin() {

        LoginRequest request = new LoginRequest();
        request.setEmail("naoexiste@email.com");
        request.setSenha("12345678");

        when(usuarioRepository.findByEmail("naoexiste@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                RegraNegocioException.class,
                () -> authService.login(request)
        );
    }

    @Test
    void deveLancarErroQuandoSenhaEstiverIncorreta() {

        LoginRequest request = new LoginRequest();
        request.setEmail("leanderson@email.com");
        request.setSenha("senha-errada");

        Usuario usuario = new Usuario(
                1L,
                "Leanderson Lima",
                "leanderson@email.com",
                "senha-criptografada"
        );

        when(usuarioRepository.findByEmail("leanderson@email.com"))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches("senha-errada", "senha-criptografada"))
                .thenReturn(false);

        assertThrows(
                RegraNegocioException.class,
                () -> authService.login(request)
        );
    }
}