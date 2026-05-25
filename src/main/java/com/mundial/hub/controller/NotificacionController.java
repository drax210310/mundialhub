package com.mundial.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.mundial.hub.model.Notificacion;
import com.mundial.hub.service.NotificacionService;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

	@Autowired
	private NotificacionService service;

	@GetMapping
	public List<Notificacion> listar() {
		return service.listar();
	}

	@PostMapping
	public Notificacion crear(@RequestBody Notificacion n) {
		return service.guardar(n);
	}
}
