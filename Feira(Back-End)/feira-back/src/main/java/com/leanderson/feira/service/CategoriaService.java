package com.leanderson.feira.service;

import com.leanderson.feira.dto.CategoriaRequest;
import com.leanderson.feira.dto.CategoriaResponse;
import com.leanderson.feira.entity.Categoria;
import com.leanderson.feira.exception.RegraNegocioException;
import com.leanderson.feira.repository.CategoriaRepository;
import com.leanderson.feira.repository.FeiranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final FeiranteRepository feiranteRepository;

    public CategoriaService(CategoriaRepository categoriaRepository,
                            FeiranteRepository feiranteRepository) {
        this.categoriaRepository = categoriaRepository;
        this.feiranteRepository = feiranteRepository;
    }

    public CategoriaResponse criar(CategoriaRequest request) {
        if (categoriaRepository.existsByNome(request.getNome())) {
            throw new RegraNegocioException("Ja existe uma categoria com esse nome.");
        }

        Categoria categoria = new Categoria(
                null,
                request.getNome(),
                request.getDescricao()
        );

        categoria.validar();

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return toResponse(categoriaSalva);
    }

    public List<CategoriaResponse> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = buscarEntidadePorId(id);
        return toResponse(categoria);
    }

    public void remover(Long id) {
        if (feiranteRepository.existsByCategoriaId(id)) {
            throw new RegraNegocioException("Não é permitido remover categoria em uso.");
        }

        Categoria categoria = buscarEntidadePorId(id);
        categoriaRepository.delete(categoria);
    }

    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        Categoria categoria = buscarEntidadePorId(id);

        if (!categoria.getNome().equals(request.getNome())
                && categoriaRepository.existsByNome(request.getNome())) {
            throw new RegraNegocioException("Ja existe uma categoria com esse nome.");
        }

        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());

        categoria.validar();

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);

        return toResponse(categoriaAtualizada);
    }

    private Categoria buscarEntidadePorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada."));
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}