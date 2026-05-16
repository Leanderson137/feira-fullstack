package com.leanderson.feira.service;

import com.leanderson.feira.dto.FeiranteRequest;
import com.leanderson.feira.dto.FeiranteResponse;
import com.leanderson.feira.entity.Categoria;
import com.leanderson.feira.entity.Feirante;
import com.leanderson.feira.entity.Usuario;
import com.leanderson.feira.exception.RegraNegocioException;
import com.leanderson.feira.repository.CategoriaRepository;
import com.leanderson.feira.repository.FeiranteRepository;
import com.leanderson.feira.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.leanderson.feira.dto.CategoriaResponse;

import java.util.List;

@Service
public class FeiranteService {

    private final FeiranteRepository feiranteRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public FeiranteService(
            FeiranteRepository feiranteRepository,
            CategoriaRepository categoriaRepository,
            UsuarioRepository usuarioRepository) {
        this.feiranteRepository = feiranteRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public FeiranteResponse criar(FeiranteRequest request) {

        Usuario usuario = buscarUsuarioLogado();

        if (feiranteRepository.existsByCpfAndUsuarioEmail(request.getCpf(), usuario.getEmail())) {
            throw new RegraNegocioException("Ja existe um feirante com esse CPF.");
        }

        Categoria categoria = buscarCategoriaDoUsuario(request.getCategoriaId(), usuario);

        Feirante feirante = new Feirante(
                null,
                request.getNome(),
                request.getCpf(),
                request.isAtivo(),
                categoria,
                usuario
        );

        feirante.validar();

        Feirante feiranteSalvo = feiranteRepository.save(feirante);

        return converterParaResponse(feiranteSalvo);
    }

    public List<FeiranteResponse> listarTodos() {

        Usuario usuario = buscarUsuarioLogado();

        return feiranteRepository.findByUsuarioEmail(usuario.getEmail())
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public FeiranteResponse buscarPorId(Long id) {

        Feirante feirante = buscarFeiranteDoUsuario(id);

        return converterParaResponse(feirante);
    }

    public FeiranteResponse atualizar(Long id, FeiranteRequest request) {

        Usuario usuario = buscarUsuarioLogado();

        Feirante feiranteExistente = buscarFeiranteDoUsuario(id);

        if (!feiranteExistente.getCpf().equals(request.getCpf())
                && feiranteRepository.existsByCpfAndUsuarioEmail(request.getCpf(), usuario.getEmail())) {
            throw new RegraNegocioException("Ja existe um feirante com esse CPF.");
        }

        Categoria categoria = buscarCategoriaDoUsuario(request.getCategoriaId(), usuario);

        feiranteExistente.setNome(request.getNome());
        feiranteExistente.setCpf(request.getCpf());
        feiranteExistente.setAtivo(request.isAtivo());
        feiranteExistente.setCategoria(categoria);
        feiranteExistente.setUsuario(usuario);

        feiranteExistente.validar();

        Feirante feiranteAtualizado = feiranteRepository.save(feiranteExistente);

        return converterParaResponse(feiranteAtualizado);
    }

    public void remover(Long id) {

        Feirante feirante = buscarFeiranteDoUsuario(id);

        feiranteRepository.delete(feirante);
    }

    private Usuario buscarUsuarioLogado() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado."));
    }

    private Categoria buscarCategoriaDoUsuario(Long categoriaId, Usuario usuario) {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada."));

        if (categoria.getUsuario() == null
                || !categoria.getUsuario().getEmail().equals(usuario.getEmail())) {
            throw new RegraNegocioException("Categoria não pertence ao usuário logado.");
        }

        return categoria;
    }

    private Feirante buscarFeiranteDoUsuario(Long id) {

        Usuario usuario = buscarUsuarioLogado();

        Feirante feirante = feiranteRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Feirante não encontrado."));

        if (feirante.getUsuario() == null
                || !feirante.getUsuario().getEmail().equals(usuario.getEmail())) {
            throw new RegraNegocioException("Feirante não pertence ao usuário logado.");
        }

        return feirante;
    }

    private FeiranteResponse converterParaResponse(Feirante feirante) {

        CategoriaResponse categoriaResponse = new CategoriaResponse(
                feirante.getCategoria().getId(),
                feirante.getCategoria().getNome(),
                feirante.getCategoria().getDescricao()
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