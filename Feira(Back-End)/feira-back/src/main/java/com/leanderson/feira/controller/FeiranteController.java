package com.leanderson.feira.controller;

import com.leanderson.feira.dto.FeiranteRequest;
import com.leanderson.feira.dto.FeiranteResponse;
import com.leanderson.feira.service.FeiranteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feirante")
@CrossOrigin(origins = "http://localhost:4200")
public class FeiranteController {

    private final FeiranteService feiranteService;

    public FeiranteController(FeiranteService feiranteService) {
        this.feiranteService = feiranteService;
    }

    @PostMapping
    public ResponseEntity<FeiranteResponse> criar(@Valid @RequestBody FeiranteRequest request) {
        FeiranteResponse feirante = feiranteService.criar(request);
        return ResponseEntity.ok(feirante);
    }

    @GetMapping
    public ResponseEntity<List<FeiranteResponse>> listarTodos() {
        List<FeiranteResponse> feirantes = feiranteService.listarTodos();
        return ResponseEntity.ok(feirantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeiranteResponse> buscarPorId(@PathVariable Long id) {
        FeiranteResponse feirante = feiranteService.buscarPorId(id);
        return ResponseEntity.ok(feirante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeiranteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FeiranteRequest request) {

        FeiranteResponse feirante = feiranteService.atualizar(id, request);
        return ResponseEntity.ok(feirante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        feiranteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}