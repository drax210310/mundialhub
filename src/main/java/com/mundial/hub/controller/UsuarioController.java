package com.mundial.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mundial.hub.model.Usuario;
import com.mundial.hub.repository.UsuarioRepository;
import com.mundial.hub.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private UsuarioRepository repo;

	// =========================
	// REGISTRO CON CONTROL DE ERRORES
	// =========================
	@PostMapping("/registro")
	public ResponseEntity<?> registrar(@RequestBody Usuario u) {
		try {
			// Intentamos registrar
			Usuario nuevoUsuario = service.registrar(u);
			return ResponseEntity.ok(nuevoUsuario);

		} catch (IllegalArgumentException e) {
			// Si falla una validación, devolvemos un error 400 (Bad Request) con el mensaje
			// exacto
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			// Para cualquier otro error no contemplado
			return ResponseEntity.internalServerError().body("Ocurrió un error inesperado en el servidor.");
		}
	}

	// =========================
	// LISTAR
	// =========================
	@GetMapping
	public List<Usuario> listar() {
		return repo.findAll();
	}
}