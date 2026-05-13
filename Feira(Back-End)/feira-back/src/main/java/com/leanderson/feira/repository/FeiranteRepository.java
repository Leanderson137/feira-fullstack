package com.leanderson.feira.repository;

import com.leanderson.feira.entity.Feirante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeiranteRepository extends JpaRepository<Feirante, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByCategoriaId(Long categoriaId);
}