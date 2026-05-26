package com.mundial.hub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.mundial.hub.model.Usuario;
import com.mundial.hub.model.Lamina;
import com.mundial.hub.repository.LaminaRepository;
import com.mundial.hub.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

	@Mock
	private UsuarioRepository usuarioRepo;

	@Mock
	private LaminaRepository laminaRepo;

	@Mock
	private Authentication auth;

	@InjectMocks
	private AlbumService albumService;

	@Test
	public void testAbrirSobreSinSaldoLanzaExcepcion() {
		Usuario usuarioFalso = new Usuario();
		usuarioFalso.setUsername("juanito");
		usuarioFalso.setMonedasCambio(2);

		when(auth.getName()).thenReturn("juanito");
		when(usuarioRepo.findByUsername("juanito")).thenReturn(Optional.of(usuarioFalso));
		when(laminaRepo.findAll()).thenReturn(Collections.singletonList(new Lamina()));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			albumService.abrirSobre(auth);
		});

		assertEquals("Saldo insuficiente.", exception.getMessage());
		System.out.println("Test pasado: El sistema bloqueó la compra por falta de saldo.");
	}
}