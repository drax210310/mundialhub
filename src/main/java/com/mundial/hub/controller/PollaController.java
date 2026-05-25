package com.mundial.hub.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mundial.hub.model.*;
import com.mundial.hub.service.PollaService;

@RestController
@RequestMapping("/api/pollas")
@CrossOrigin
public class PollaController {

	@Autowired
	private PollaService service;

	@PostMapping("/crear")
	public Polla crearPolla(@RequestParam String nombre, Authentication auth) {
		return service.crearPolla(nombre, auth);
	}

	@PostMapping("/unirse")
	public Polla unirsePolla(@RequestParam String codigo, Authentication auth) {
		return service.unirsePolla(codigo, auth);
	}

	@GetMapping("/mis-pollas")
	public List<Polla> misPollas(Authentication auth) {
		return service.misPollas(auth);
	}

	@PostMapping("/pronosticar")
	public Pronostico pronosticar(@RequestParam Long pollaId, @RequestParam Long partidoId,
			@RequestParam Integer golesLocal, @RequestParam Integer golesVisitante, Authentication auth) {

		return service.guardarPronostico(pollaId, partidoId, golesLocal, golesVisitante, auth);
	}

	// 🔥 NUEVO: Endpoint para consultar la tabla de posiciones
	@GetMapping("/{pollaId}/ranking")
	public List<UsuarioRankingDTO> obtenerRanking(@PathVariable Long pollaId) {
		return service.obtenerRanking(pollaId);
	}
}