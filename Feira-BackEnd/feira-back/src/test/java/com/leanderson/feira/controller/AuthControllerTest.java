package com.leanderson.feira.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leanderson.feira.dto.LoginRequest;
import com.leanderson.feira.dto.LoginResponse;
import com.leanderson.feira.dto.UsuarioRequest;
import com.leanderson.feira.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void configurar() {
        mockMvc = standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveCadastrarUsuario() throws Exception {

        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Leanderson Lima");
        request.setEmail("leanderson@email.com");
        request.setSenha("12345678");

        mockMvc.perform(post("/auth/cadastrar")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(authService).cadastrar(any(UsuarioRequest.class));
    }

    @Test
    void deveRealizarLogin() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("leanderson@email.com");
        request.setSenha("12345678");

        LoginResponse response = new LoginResponse(
                "token-jwt",
                "Leanderson Lima"
        );

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-jwt"))
                .andExpect(jsonPath("$.nome").value("Leanderson Lima"))
                .andExpect(jsonPath("$.tipo").value("Bearer"));

        verify(authService).login(any(LoginRequest.class));
    }
}