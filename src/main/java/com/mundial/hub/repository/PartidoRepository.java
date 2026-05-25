package com.mundial.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mundial.hub.model.Partido;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
}
