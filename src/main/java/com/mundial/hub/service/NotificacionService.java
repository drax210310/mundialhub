package com.mundial.hub.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mundial.hub.model.Notificacion;
import com.mundial.hub.model.Usuario;
import com.mundial.hub.repository.NotificacionRepository;

@Service
public class NotificacionService {

	@Autowired
	private NotificacionRepository repo;

	public List<Notificacion> misNotificaciones(String username) {
		return repo.findByUsuarioUsernameOrderByFechaDesc(username);
	}

	public void enviarNotificacion(Usuario usuario, String mensaje, String tipo) {
		Notificacion n = new Notificacion(mensaje, tipo, usuario);
		repo.save(n);

	}
}