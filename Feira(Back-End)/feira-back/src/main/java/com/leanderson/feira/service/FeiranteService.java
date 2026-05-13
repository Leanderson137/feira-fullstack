package com.leanderson.feira.service;

import com.leanderson.feira.dto.CategoriaResponse;
import com.leanderson.feira.dto.FeiranteRequest;
import com.leanderson.feira.dto.FeiranteResponse;
import com.leanderson.feira.entity.Categoria;
import com.leanderson.feira.entity.Feirante;
import com.leanderson.feira.exception.RegraNegocioException;
import com.leanderson.feira.repository.CategoriaRepository;
import com.leanderson.feira.repository.FeiranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeiranteService {

    private final FeiranteRepository feiranteRepository;
    private final CategoriaRepository categoriaRepository;

    public FeiranteService(FeiranteRepository feiranteRepository,
                           CategoriaRepository categoriaRepository) {
        this.feiranteRepository = feiranteRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public FeiranteResponse criar(FeiranteRequest request) {
        if (feiranteRepository.existsByCpf(request.getCpf())) {
            throw new RegraNegocioException("Ja existe um feirante com esse CPF.");
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada."));

        Feirante feirante = new Feirante(
                null,
                request.getNome(),
                request.getCpf(),
                request.isAtivo(),
                categoria
        );

        feirante.validar();

        Feirante feiranteSalvo = feiranteRepository.save(feirante);

        return toResponse(feiranteSalvo);
    }

    public List<FeiranteResponse> listarTodos() {
        return feiranteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public FeiranteResponse buscarPorId(Long id) {
        Feirante feirante = buscarEntidadePorId(id);
        return toResponse(feirante);
    }

    public FeiranteResponse atualizar(Long id, FeiranteRequest request) {
        Feirante feiranteExistente = buscarEntidadePorId(id);

        if (!feiranteExistente.getCpf().equals(request.getCpf())
                && feiranteRepository.existsByCpf(request.getCpf())) {
            throw new RegraNegocioException("Ja existe um feirante com esse CPF.");
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada."));

        Feirante feirante = new Feirante(
                id,
                request.getNome(),
                request.getCpf(),
                request.isAtivo(),
                categoria
        );

        feirante.validar();

        Feirante feiranteAtualizado = feiranteRepository.save(feirante);

        return toResponse(feiranteAtualizado);
    }

    public void remover(Long id) {
        Feirante feirante = buscarEntidadePorId(id);
        feiranteRepository.delete(feirante);
    }

    private Feirante buscarEntidadePorId(Long id) {
        return feiranteRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Feirante não encontrado."));
    }

    private FeiranteResponse toResponse(Feirante feirante) {
        Categoria categoria = feirante.getCategoria();

        CategoriaResponse categoriaResponse = new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );

        return new FeiranteResponse(
                feirante.getId(),
                feirante.getNome(),
                feirante.getCpf(),
                feirante.getAtivo(),
                categoriaResponse
        );
    }
}