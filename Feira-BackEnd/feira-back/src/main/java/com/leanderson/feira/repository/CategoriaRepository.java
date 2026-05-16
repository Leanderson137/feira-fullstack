package com.leanderson.feira.repository;

import com.leanderson.feira.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNome(String nome);

    boolean existsByNomeAndUsuarioEmail(String nome, String email);

    List<Categoria> findByUsuarioEmail(String email);
}