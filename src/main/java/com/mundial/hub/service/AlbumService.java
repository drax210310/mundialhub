package com.mundial.hub.service;

import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.mundial.hub.model.*;
import com.mundial.hub.repository.*;

@Service
public class AlbumService {

	private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);

	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private LaminaRepository laminaRepo;
	@Autowired
	private OfertaMarketRepository ofertaRepo;

	public List<Lamina> misLaminas(Authentication auth) {
		return usuarioRepo.findByUsername(auth.getName()).orElseThrow().getLaminas();
	}

	public List<Lamina> misRepetidas(Authentication auth) {
		return usuarioRepo.findByUsername(auth.getName()).orElseThrow().getRepetidas();
	}

	public Integer misMonedas(Authentication auth) {
		Usuario u = usuarioRepo.findByUsername(auth.getName()).orElseThrow();
		return u.getMonedasCambio() != null ? u.getMonedasCambio() : 0;
	}

	public List<Lamina> todasLasLaminas() {
		return laminaRepo.findAll();
	}

	public List<OfertaMarket> verMercadoComunidad() {
		return ofertaRepo.findByActivaTrue();
	}

	public void recargarMonedas(Integer cantidad, Authentication auth) {
		Usuario usuario = usuarioRepo.findByUsername(auth.getName()).orElseThrow();
		int saldoActual = (usuario.getMonedasCambio() != null ? usuario.getMonedasCambio() : 0);
		usuario.setMonedasCambio(saldoActual + cantidad);
		usuarioRepo.save(usuario);
		logger.info("ECONOMÍA: Usuario {} recargó {} monedas.", auth.getName(), cantidad);
	}

	public void abrirSobre(Authentication auth) {
		Usuario usuario = usuarioRepo.findByUsername(auth.getName()).orElseThrow();
		List<Lamina> todas = laminaRepo.findAll();
		if (todas.isEmpty())
			throw new RuntimeException("No hay láminas registradas.");

		int COSTO = 5;
		if ((usuario.getMonedasCambio() != null ? usuario.getMonedasCambio() : 0) < COSTO) {
			throw new RuntimeException("Saldo insuficiente.");
		}

		usuario.setMonedasCambio(usuario.getMonedasCambio() - COSTO);
		Random r = new Random();

		for (int i = 0; i < 3; i++) {
			Lamina lam = todas.get(r.nextInt(todas.size()));
			if (usuario.getLaminas().contains(lam)) {
				usuario.getRepetidas().add(lam);
			} else {
				usuario.getLaminas().add(lam);
			}
		}
		usuarioRepo.save(usuario);
		logger.info("ALBUM: Usuario {} abrió un sobre.", auth.getName());
	}

	public void comprarAlSistema(Long laminaId, Authentication auth) {
		Usuario usuario = usuarioRepo.findByUsername(auth.getName()).orElseThrow();
		Lamina lamina = laminaRepo.findById(laminaId).orElseThrow();

		if (usuario.getLaminas().contains(lamina))
			throw new RuntimeException("Ya tienes esta lámina.");
		if (usuario.getMonedasCambio() < lamina.getValorMercado()) {
			throw new RuntimeException("Monedas insuficientes.");
		}

		usuario.setMonedasCambio(usuario.getMonedasCambio() - lamina.getValorMercado());
		usuario.getLaminas().add(lamina);
		usuarioRepo.save(usuario);
	}

	public void publicarOferta(Long laminaId, Integer precio, Authentication auth) {
		Usuario usuario = usuarioRepo.findByUsername(auth.getName()).orElseThrow();
		Lamina lamina = laminaRepo.findById(laminaId).orElseThrow();

		if (!usuario.getRepetidas().contains(lamina)) {
			throw new RuntimeException("No tienes esta lámina repetida.");
		}

		usuario.getRepetidas().remove(lamina);
		usuarioRepo.save(usuario);

		OfertaMarket oferta = new OfertaMarket(usuario, lamina, precio);
		ofertaRepo.save(oferta);
	}

	public void comprarOfertaP2P(Long ofertaId, Authentication auth) {
		Usuario comprador = usuarioRepo.findByUsername(auth.getName()).orElseThrow();
		OfertaMarket oferta = ofertaRepo.findById(ofertaId).orElseThrow();

		if (!oferta.getActiva())
			throw new RuntimeException("Oferta no disponible.");
		if (comprador.getMonedasCambio() < oferta.getPrecio())
			throw new RuntimeException("Saldo insuficiente.");

		Usuario vendedor = oferta.getVendedor();
		comprador.setMonedasCambio(comprador.getMonedasCambio() - oferta.getPrecio());
		vendedor.setMonedasCambio(
				(vendedor.getMonedasCambio() != null ? vendedor.getMonedasCambio() : 0) + oferta.getPrecio());

		if (comprador.getLaminas().contains(oferta.getLamina())) {
			comprador.getRepetidas().add(oferta.getLamina());
		} else {
			comprador.getLaminas().add(oferta.getLamina());
		}

		oferta.setActiva(false);
		usuarioRepo.save(comprador);
		usuarioRepo.save(vendedor);
		ofertaRepo.save(oferta);
	}
}