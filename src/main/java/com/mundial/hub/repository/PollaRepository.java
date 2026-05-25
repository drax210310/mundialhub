package com.mundial.hub.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mundial.hub.model.Polla;

public interface PollaRepository extends JpaRepository<Polla, Long> {
	Optional<Polla> findByCodigoInvitacion(String codigoInvitacion);

	List<Polla> findByParticipantesUsername(String username);
}