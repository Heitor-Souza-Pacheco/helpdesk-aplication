package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    List<Pergunta> findByUsuarioId(Long id);
    List<Pergunta> findByCategoriaPergunta(String categoria);
}
