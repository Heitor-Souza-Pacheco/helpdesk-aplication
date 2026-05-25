package com.example.demo.repository;

import com.example.demo.entities.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerguntaRepository  extends JpaRepository<Pergunta, Long> {
    Optional<Pergunta> findByUsuarioId(Long id);
}
