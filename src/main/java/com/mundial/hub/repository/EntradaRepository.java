package com.mundial.hub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundial.hub.model.Entrada;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

	List<Entrada> findByUsuarioUsername(String username);

	Optional<Entrada> findByUsuarioUsernameAndPartidoId(String username, Long partidoId);

	List<Entrada> findByUsuarioUsernameAndEstado(String username, com.mundial.hub.model.EstadoEntrada estado);
}