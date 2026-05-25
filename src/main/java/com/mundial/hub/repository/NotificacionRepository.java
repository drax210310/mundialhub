package com.mundial.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mundial.hub.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}