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


	@PostMapping("/registro")
	public ResponseEntity<?> registrar(@RequestBody Usuario u) {
		try {
			Usuario nuevoUsuario = service.registrar(u);
			return ResponseEntity.ok(nuevoUsuario);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Ocurrió un error inesperado en el servidor.");
		}
	}

	@GetMapping
	public List<Usuario> listar() {
		return repo.findAll();
	}
}