package com.mundial.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mundial.hub.model.Usuario;
import com.mundial.hub.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Usuario registrar(Usuario u) {
		// 1. Validar campos nulos o vacíos
		if (u.getNombre() == null || u.getNombre().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre completo es obligatorio.");
		}

		// 2. Validar Username (Mínimo 4 caracteres, sin espacios)
		if (u.getUsername() == null || u.getUsername().length() < 4 || u.getUsername().contains(" ")) {
			throw new IllegalArgumentException(
					"El nombre de usuario debe tener al menos 4 caracteres y no contener espacios.");
		}

		// 3. Validar Correo Electrónico (Debe tener formato usuario@dominio.com)
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
		if (u.getEmail() == null || !u.getEmail().matches(emailRegex)) {
			throw new IllegalArgumentException("El formato del correo electrónico no es válido.");
		}

		// 4. Validar Contraseña (Mínimo 6 caracteres)
		if (u.getPassword() == null || u.getPassword().length() < 6) {
			throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
		}

		// 5. Validar que no existan duplicados en Base de Datos
		if (repo.existsByUsername(u.getUsername())) {
			throw new IllegalArgumentException("El nombre de usuario '@" + u.getUsername() + "' ya está en uso.");
		}
		if (repo.existsByEmail(u.getEmail())) {
			throw new IllegalArgumentException("El correo electrónico ya está registrado en otra cuenta.");
		}

		// Si todo está correcto, ciframos y guardamos
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		u.setRol("USUARIO");
		u.setMonedasCambio(0); // Inicializamos sus monedas

		return repo.save(u);
	}
}