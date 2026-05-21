package com.leanderson.feira.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leanderson.feira.dto.CategoriaResponse;
import com.leanderson.feira.dto.FeiranteRequest;
import com.leanderson.feira.dto.FeiranteResponse;
import com.leanderson.feira.service.FeiranteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class FeiranteControllerTest {

    @Mock
    private FeiranteService feiranteService;

    @InjectMocks
    private FeiranteController feiranteController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void configurar() {
        mockMvc = standaloneSetup(feiranteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveListarFeirantes() throws Exception {

        CategoriaResponse categoria = new CategoriaResponse(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças"
        );

        FeiranteResponse feirante = new FeiranteResponse(
                1L,
                "João Silva",
                "12345678901",
                true,
                categoria
        );

        when(feiranteService.listarTodos())
                .thenReturn(List.of(feirante));

        mockMvc.perform(get("/feirante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[0].cpf").value("12345678901"))
                .andExpect(jsonPath("$[0].ativo").value(true))
                .andExpect(jsonPath("$[0].categoria.nome").value("Verduras"));

        verify(feiranteService).listarTodos();
    }

    @Test
    void deveBuscarFeirantePorId() throws Exception {

        CategoriaResponse categoria = new CategoriaResponse(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças"
        );

        FeiranteResponse feirante = new FeiranteResponse(
                1L,
                "João Silva",
                "12345678901",
                true,
                categoria
        );

        when(feiranteService.buscarPorId(1L))
                .thenReturn(feirante);

        mockMvc.perform(get("/feirante/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.ativo").value(true))
                .andExpect(jsonPath("$.categoria.nome").value("Verduras"));

        verify(feiranteService).buscarPorId(1L);
    }

    @Test
    void deveCriarFeirante() throws Exception {

        FeiranteRequest request = new FeiranteRequest();
        request.setNome("João Silva");
        request.setCpf("12345678901");
        request.setAtivo(true);
        request.setCategoriaId(1L);

        CategoriaResponse categoria = new CategoriaResponse(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças"
        );

        FeiranteResponse response = new FeiranteResponse(
                1L,
                "João Silva",
                "12345678901",
                true,
                categoria
        );

        when(feiranteService.criar(any(FeiranteRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/feirante")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.ativo").value(true))
                .andExpect(jsonPath("$.categoria.nome").value("Verduras"));

        verify(feiranteService).criar(any(FeiranteRequest.class));
    }

    @Test
    void deveAtualizarFeirante() throws Exception {

        FeiranteRequest request = new FeiranteRequest();
        request.setNome("Maria Silva");
        request.setCpf("12345678901");
        request.setAtivo(false);
        request.setCategoriaId(1L);

        CategoriaResponse categoria = new CategoriaResponse(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças"
        );

        FeiranteResponse response = new FeiranteResponse(
                1L,
                "Maria Silva",
                "12345678901",
                false,
                categoria
        );

        when(feiranteService.atualizar(eq(1L), any(FeiranteRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/feirante/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Maria Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.ativo").value(false))
                .andExpect(jsonPath("$.categoria.nome").value("Verduras"));

        verify(feiranteService).atualizar(eq(1L), any(FeiranteRequest.class));
    }

    @Test
    void deveRemoverFeirante() throws Exception {

        mockMvc.perform(delete("/feirante/1"))
                .andExpect(status().is2xxSuccessful());

        verify(feiranteService).remover(1L);
    }
}