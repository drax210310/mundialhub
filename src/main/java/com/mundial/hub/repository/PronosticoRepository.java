package com.mundial.hub.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mundial.hub.model.Pronostico;

public interface PronosticoRepository extends JpaRepository<Pronostico, Long> {
	Optional<Pronostico> findByUsuarioUsernameAndPartidoIdAndPollaId(String username, Long partidoId, Long pollaId);

	List<Pronostico> findByPartidoId(Long partidoId);

	// 🔥 NUEVO: Buscar pronósticos de un usuario en una polla específica para sumar
	// sus puntos
	List<Pronostico> findByUsuarioUsernameAndPollaId(String username, Long pollaId);
}