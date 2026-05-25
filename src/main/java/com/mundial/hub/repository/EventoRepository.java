package com.mundial.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mundial.hub.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}