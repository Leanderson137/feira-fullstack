package com.leanderson.feira.controller;

import com.leanderson.feira.dto.UsuarioResponse;
import com.leanderson.feira.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @BeforeEach
    void configurar() {
        mockMvc = standaloneSetup(usuarioController).build();
    }

    @Test
    void deveListarUsuarios() throws Exception {

        UsuarioResponse usuario1 = new UsuarioResponse(
                1L,
                "Leanderson Lima",
                "leanderson@email.com"
        );

        UsuarioResponse usuario2 = new UsuarioResponse(
                2L,
                "Maria Silva",
                "maria@email.com"
        );

        when(usuarioService.listarTodos())
                .thenReturn(List.of(usuario1, usuario2));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Leanderson Lima"))
                .andExpect(jsonPath("$[0].email").value("leanderson@email.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Maria Silva"))
                .andExpect(jsonPath("$[1].email").value("maria@email.com"));

        verify(usuarioService).listarTodos();
    }

    @Test
    void deveRemoverUsuario() throws Exception {

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().is2xxSuccessful());

        verify(usuarioService).remover(1L);
    }
}