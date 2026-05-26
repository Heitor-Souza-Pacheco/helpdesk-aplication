package com.example.demo.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Resposta> findByUsuarioId(Long id);
    List<Resposta> findByPerguntaId(Long id);

}
