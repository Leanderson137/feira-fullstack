package com.leanderson.feira.repository;

import com.leanderson.feira.entity.Feirante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeiranteRepository extends JpaRepository<Feirante, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByCpfAndUsuarioEmail(String cpf, String email);

    boolean existsByCategoriaId(Long categoriaId);

    boolean existsByCategoriaIdAndUsuarioEmail(Long categoriaId, String email);

    List<Feirante> findByUsuarioEmail(String email);
}