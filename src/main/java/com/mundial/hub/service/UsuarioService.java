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
		if (u.getNombre() == null || u.getNombre().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre completo es obligatorio.");
		}

		if (u.getUsername() == null || u.getUsername().length() < 4 || u.getUsername().contains(" ")) {
			throw new IllegalArgumentException(
					"El nombre de usuario debe tener al menos 4 caracteres y no contener espacios.");
		}

		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
		if (u.getEmail() == null || !u.getEmail().matches(emailRegex)) {
			throw new IllegalArgumentException("El formato del correo electrónico no es válido.");
		}

		if (u.getPassword() == null || u.getPassword().length() < 6) {
			throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
		}

		if (repo.existsByUsername(u.getUsername())) {
			throw new IllegalArgumentException("El nombre de usuario '@" + u.getUsername() + "' ya está en uso.");
		}
		if (repo.existsByEmail(u.getEmail())) {
			throw new IllegalArgumentException("El correo electrónico ya está registrado en otra cuenta.");
		}

		u.setPassword(passwordEncoder.encode(u.getPassword()));
		u.setRol("USUARIO");
		u.setMonedasCambio(0);

		return repo.save(u);
	}
}