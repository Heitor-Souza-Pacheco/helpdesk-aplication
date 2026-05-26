package com.example.demo.repository;

import com.example.demo.entities.Pergunta;
import com.example.demo.entities.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Pergunta> findByUsuarioId(Long id);
    List<Pergunta> findByPerguntaId(Long id);
}
