package com.leanderson.feira.service;

import com.leanderson.feira.dto.LoginRequest;
import com.leanderson.feira.dto.LoginResponse;
import com.leanderson.feira.dto.UsuarioRequest;
import com.leanderson.feira.entity.Usuario;
import com.leanderson.feira.exception.RegraNegocioException;
import com.leanderson.feira.repository.UsuarioRepository;
import com.leanderson.feira.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void cadastrar(UsuarioRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RegraNegocioException("Já existe um usuário com esse e-mail.");
        }

        Usuario usuario = new Usuario(
                null,
                request.getNome(),
                request.getEmail(),
                passwordEncoder.encode(request.getSenha())
        );

        usuario.validar();

        usuarioRepository.save(usuario);
    }

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RegraNegocioException("E-mail ou senha inválidos."));

        boolean senhaCorreta = passwordEncoder.matches(
                request.getSenha(),
                usuario.getSenha()
        );

        if (!senhaCorreta) {
            throw new RegraNegocioException("E-mail ou senha inválidos.");
        }

        String token = jwtService.gerarToken(usuario.getEmail());

        return new LoginResponse(
                token,
                usuario.getNome()
        );
    }
}