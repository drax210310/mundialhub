package com.mundial.hub.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mundial.hub.model.Partido;
import com.mundial.hub.service.PartidoService;
import com.mundial.hub.service.PartidoSyncService;
import com.mundial.hub.service.PollaService;
@RestController
@RequestMapping("/api/partidos")
@CrossOrigin(origins = "http://localhost:8081")
public class PartidoController {

	@Autowired
	private PartidoService service;
	@Autowired
	private PollaService pollaService;
	@Autowired
	private PartidoSyncService syncService;

	@GetMapping
	public List<Partido> listar() {
		return service.obtenerPartidos();
	}

	@PostMapping
	public Partido guardar(@RequestBody Partido p) {
		return service.guardar(p);
	}

	@PostMapping("/finalizar/{id}")
	public Partido finalizarPartido(@PathVariable Long id, @RequestParam Integer golesLocal,
			@RequestParam Integer golesVisitante) {
		Partido partido = service.obtenerPartidos().stream().filter(p -> p.getId().equals(id)).findFirst()
				.orElseThrow();
		partido.setEstado("FINALIZADO");
		partido.setGolesLocal(golesLocal);
		partido.setGolesVisitante(golesVisitante);
		Partido actualizado = service.guardar(partido);
		pollaService.calcularPuntos(id, golesLocal, golesVisitante);
		return actualizado;
	}

	@PostMapping("/sincronizar")
	public void sincronizarPartidos(@RequestParam(defaultValue = "false") boolean forzarFalla) {
		syncService.sincronizarConAPI(forzarFalla);
	}
}