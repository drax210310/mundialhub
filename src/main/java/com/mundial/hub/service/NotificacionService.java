package com.mundial.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.mundial.hub.model.Notificacion;
import com.mundial.hub.repository.NotificacionRepository;

@Service
public class NotificacionService {

	@Autowired
	private NotificacionRepository repo;

	public List<Notificacion> listar() {
		return repo.findAll();
	}

	public Notificacion guardar(Notificacion n) {
		return repo.save(n);
	}
}
