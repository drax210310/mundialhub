package com.mundial.hub.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mundial.hub.model.Notificacion;
import com.mundial.hub.service.NotificacionService;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "http://localhost:8081")
public class NotificacionController {

	@Autowired
	private NotificacionService service;

	@GetMapping("/mis-notificaciones")
	public List<Notificacion> misNotificaciones(Authentication auth) {
		return service.misNotificaciones(auth.getName());
	}
}