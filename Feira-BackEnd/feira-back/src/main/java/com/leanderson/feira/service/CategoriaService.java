package com.leanderson.feira.service;

import com.leanderson.feira.dto.CategoriaRequest;
import com.leanderson.feira.dto.CategoriaResponse;
import com.leanderson.feira.entity.Categoria;
import com.leanderson.feira.entity.Usuario;
import com.leanderson.feira.exception.RegraNegocioException;
import com.leanderson.feira.repository.CategoriaRepository;
import com.leanderson.feira.repository.FeiranteRepository;
import com.leanderson.feira.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final FeiranteRepository feiranteRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaService(
            CategoriaRepository categoriaRepository,
            FeiranteRepository feiranteRepository,
            UsuarioRepository usuarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.feiranteRepository = feiranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public CategoriaResponse criar(CategoriaRequest request) {

        Usuario usuario = buscarUsuarioLogado();

        if (categoriaRepository.existsByNomeAndUsuarioEmail(request.getNome(), usuario.getEmail())) {
            throw new RegraNegocioException("Já existe uma categoria com esse nome.");
        }

        Categoria categoria = new Categoria(
                null,
                request.getNome(),
                request.getDescricao(),
                usuario
        );

        categoria.validar();

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return converterParaResponse(categoriaSalva);
    }

    public List<CategoriaResponse> listarTodas() {

        Usuario usuario = buscarUsuarioLogado();

        return categoriaRepository.findByUsuarioEmail(usuario.getEmail())
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public CategoriaResponse buscarPorId(Long id) {

        Categoria categoria = buscarCategoriaDoUsuario(id);

        return converterParaResponse(categoria);
    }

    public void remover(Long id) {

        Categoria categoria = buscarCategoriaDoUsuario(id);

        if (feiranteRepository.existsByCategoriaId(id)) {
            throw new RegraNegocioException("Não é permitido remover categoria em uso.");
        }

        categoriaRepository.delete(categoria);
    }

    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {

        Categoria categoria = buscarCategoriaDoUsuario(id);
        Usuario usuario = buscarUsuarioLogado();

        if (!categoria.getNome().equalsIgnoreCase(request.getNome())
                && categoriaRepository.existsByNomeAndUsuarioEmail(request.getNome(), usuario.getEmail())) {
            throw new RegraNegocioException("Já existe uma categoria com esse nome.");
        }

        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());

        categoria.validar();

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);

        return converterParaResponse(categoriaAtualizada);
    }

    private Usuario buscarUsuarioLogado() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado."));
    }

    private Categoria buscarCategoriaDoUsuario(Long id) {

        Usuario usuario = buscarUsuarioLogado();

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada."));

        if (categoria.getUsuario() == null ||
                !categoria.getUsuario().getEmail().equals(usuario.getEmail())) {
            throw new RegraNegocioException("Categoria não pertence ao usuário logado.");
        }

        return categoria;
    }

    private CategoriaResponse converterParaResponse(Categoria categoria) {

        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}