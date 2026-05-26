package com.mundial.hub.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mundial.hub.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
	List<Notificacion> findByUsuarioUsernameOrderByFechaDesc(String username);
}