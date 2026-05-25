package com.mundial.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.mundial.hub.model.Usuario;
import com.mundial.hub.repository.UsuarioRepository;
@SuppressWarnings("java:S4684")
@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = "http://localhost:8081")
public class PerfilController {

	@Autowired
	private UsuarioRepository repo;

	@GetMapping
	public Usuario obtenerPerfil(Authentication auth) {
		return repo.findByUsername(auth.getName()).orElseThrow();
	}

	// =====================================
	// ACTUALIZAR PERFIL CON VALIDACIONES
	// =====================================
	@PutMapping
	public ResponseEntity<?> actualizarPerfil(@RequestBody Usuario actualizacion, Authentication auth) {
		Usuario usuario = repo.findByUsername(auth.getName()).orElseThrow();

		try {
			// 1. Validar Nombre
			if (actualizacion.getNombre() == null || actualizacion.getNombre().trim().isEmpty()) {
				throw new IllegalArgumentException("El nombre completo no puede estar vacío.");
			}

			// 2. Validar formato del Correo
			String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
			if (actualizacion.getEmail() == null || !actualizacion.getEmail().matches(emailRegex)) {
				throw new IllegalArgumentException("El formato del correo electrónico no es válido.");
			}

			// 3. Validar que el nuevo correo no esté ocupado por OTRO usuario
			if (!usuario.getEmail().equals(actualizacion.getEmail()) && repo.existsByEmail(actualizacion.getEmail())) {
				throw new IllegalArgumentException("Este correo electrónico ya está registrado en otra cuenta.");
			}

			// Si pasa las validaciones, actualizamos
			usuario.setNombre(actualizacion.getNombre());
			usuario.setEmail(actualizacion.getEmail());
			usuario.setEquipoFavorito(actualizacion.getEquipoFavorito());
			usuario.setSedeFavorita(actualizacion.getSedeFavorita());

			return ResponseEntity.ok(repo.save(usuario));

		} catch (IllegalArgumentException e) {
			// Devolvemos el error al frontend (el alert() lo atrapará)
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Ocurrió un error inesperado al actualizar el perfil.");
		}
	}
}