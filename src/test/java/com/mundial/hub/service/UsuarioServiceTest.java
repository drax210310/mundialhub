package com.mundial.hub.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mundial.hub.model.Usuario;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

	@InjectMocks
	private UsuarioService usuarioService;

	@Test
	public void testRegistroCorreoInvalido() {
		Usuario u = new Usuario();
		u.setNombre("Carlos Perez");
		u.setUsername("carlitos");
		u.setEmail("correo-sin-arroba.com");
		u.setPassword("123456");

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
			usuarioService.registrar(u);
		});

		assertTrue(ex.getMessage().contains("formato del correo electrónico no es válido"));
		System.out.println("Test pasado: El sistema rechazó el correo sin formato @.");
	}
}