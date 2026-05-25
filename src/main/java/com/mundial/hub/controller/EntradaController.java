package com.mundial.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.mundial.hub.model.Entrada;
import com.mundial.hub.service.EntradaService;

@RestController
@RequestMapping("/api/entradas")
@CrossOrigin(origins = "http://localhost:8081")
public class EntradaController {

	@Autowired
	private EntradaService service;

	@PostMapping("/crear/{partidoId}")
	public Entrada reservar(@PathVariable Long partidoId, Authentication auth) {
		return service.reservarEntrada(partidoId, auth);
	}

	@GetMapping("/mis-entradas")
	public List<Entrada> misEntradas(Authentication auth) {
		return service.misEntradas(auth);
	}

	@PostMapping("/pagar/{entradaId}")
	public Entrada pagar(@PathVariable Long entradaId, Authentication auth) {
		return service.pagarEntrada(entradaId, auth);
	}

	// 🔥 NUEVO: Endpoint para transferir entradas
	@PostMapping("/transferir/{entradaId}")
	public Entrada transferir(@PathVariable Long entradaId, @RequestParam String usernameDestino, Authentication auth) {
		return service.transferirEntrada(entradaId, usernameDestino, auth);
	}
}