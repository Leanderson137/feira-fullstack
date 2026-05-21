package com.leanderson.feira.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leanderson.feira.dto.CategoriaRequest;
import com.leanderson.feira.dto.CategoriaResponse;
import com.leanderson.feira.service.CategoriaService;
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
class CategoriaControllerTest {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void configurar() {
        mockMvc = standaloneSetup(categoriaController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveListarCategorias() throws Exception {

        CategoriaResponse categoria = new CategoriaResponse(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças"
        );

        when(categoriaService.listarTodas())
                .thenReturn(List.of(categoria));

        mockMvc.perform(get("/categoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Verduras"))
                .andExpect(jsonPath("$[0].descricao").value("Produtos verdes e hortaliças"));

        verify(categoriaService).listarTodas();
    }

    @Test
    void deveBuscarCategoriaPorId() throws Exception {

        CategoriaResponse categoria = new CategoriaResponse(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças"
        );

        when(categoriaService.buscarPorId(1L))
                .thenReturn(categoria);

        mockMvc.perform(get("/categoria/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Verduras"))
                .andExpect(jsonPath("$.descricao").value("Produtos verdes e hortaliças"));

        verify(categoriaService).buscarPorId(1L);
    }

    @Test
    void deveCriarCategoria() throws Exception {

        CategoriaRequest request = new CategoriaRequest();
        request.setNome("Verduras");
        request.setDescricao("Produtos verdes e hortaliças");

        CategoriaResponse response = new CategoriaResponse(
                1L,
                "Verduras",
                "Produtos verdes e hortaliças"
        );

        when(categoriaService.criar(any(CategoriaRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/categoria")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Verduras"))
                .andExpect(jsonPath("$.descricao").value("Produtos verdes e hortaliças"));

        verify(categoriaService).criar(any(CategoriaRequest.class));
    }

    @Test
    void deveAtualizarCategoria() throws Exception {

        CategoriaRequest request = new CategoriaRequest();
        request.setNome("Frutas");
        request.setDescricao("Frutas frescas");

        CategoriaResponse response = new CategoriaResponse(
                1L,
                "Frutas",
                "Frutas frescas"
        );

        when(categoriaService.atualizar(eq(1L), any(CategoriaRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/categoria/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Frutas"))
                .andExpect(jsonPath("$.descricao").value("Frutas frescas"));

        verify(categoriaService).atualizar(eq(1L), any(CategoriaRequest.class));
    }

    @Test
    void deveRemoverCategoria() throws Exception {

        mockMvc.perform(delete("/categoria/1"))
                .andExpect(status().is2xxSuccessful());

        verify(categoriaService).remover(1L);
    }
}