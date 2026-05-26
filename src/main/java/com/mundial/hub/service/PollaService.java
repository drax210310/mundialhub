package com.mundial.hub.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.mundial.hub.model.*;
import com.mundial.hub.repository.*;

@Service
public class PollaService {

	@Autowired
	private PollaRepository pollaRepo;
	@Autowired
	private PronosticoRepository pronosticoRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private PartidoRepository partidoRepo;

	public Polla crearPolla(String nombre, Authentication auth) {
		Usuario creador = usuarioRepo.findByUsername(auth.getName()).orElseThrow();

		String codigo = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

		Polla polla = new Polla(nombre, codigo, creador);
		polla.getParticipantes().add(creador);

		return pollaRepo.save(polla);
	}

	public Polla unirsePolla(String codigo, Authentication auth) {
		Usuario usuario = usuarioRepo.findByUsername(auth.getName()).orElseThrow();
		Polla polla = pollaRepo.findByCodigoInvitacion(codigo)
				.orElseThrow(() -> new RuntimeException("Código inválido"));

		if (!polla.getParticipantes().contains(usuario)) {
			polla.getParticipantes().add(usuario);
			pollaRepo.save(polla);
		}
		return polla;
	}

	public List<Polla> misPollas(Authentication auth) {
		return pollaRepo.findByParticipantesUsername(auth.getName());
	}

	public Pronostico guardarPronostico(Long pollaId, Long partidoId, Integer golesL, Integer golesV,
			Authentication auth) {
		Usuario usuario = usuarioRepo.findByUsername(auth.getName()).orElseThrow();
		Partido partido = partidoRepo.findById(partidoId).orElseThrow();
		Polla polla = pollaRepo.findById(pollaId).orElseThrow();

		if ("FINALIZADO".equals(partido.getEstado()) || "EN JUEGO".equals(partido.getEstado())) {
			throw new RuntimeException("El partido ya está en curso o finalizado. No se permiten más pronósticos.");
		}

		try {
			LocalDateTime fechaPartido = LocalDateTime.parse(partido.getFecha());
			LocalDateTime limitePermitido = fechaPartido.minusMinutes(15);

			if (LocalDateTime.now().isAfter(limitePermitido)) {
				throw new RuntimeException(
						"Tiempo agotado. Los pronósticos se cierran 15 minutos antes del inicio del partido para evitar ventajas.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error al validar la fecha del partido. Intenta más tarde.");
		}

		Optional<Pronostico> optPronostico = pronosticoRepo
				.findByUsuarioUsernameAndPartidoIdAndPollaId(usuario.getUsername(), partidoId, pollaId);

		Pronostico pronostico;
		if (optPronostico.isEmpty()) {
			pronostico = new Pronostico();

			int saldoActual = usuario.getMonedasCambio() != null ? usuario.getMonedasCambio() : 0;
			usuario.setMonedasCambio(saldoActual + 2);
			usuarioRepo.save(usuario);
		} else {
			pronostico = optPronostico.get();
		}

		pronostico.setUsuario(usuario);
		pronostico.setPartido(partido);
		pronostico.setPolla(polla);
		pronostico.setGolesLocal(golesL);
		pronostico.setGolesVisitante(golesV);

		return pronosticoRepo.save(pronostico);
	}

	public void calcularPuntos(Long partidoId, Integer resultadoRealLocal, Integer resultadoRealVisitante) {
		List<Pronostico> pronosticos = pronosticoRepo.findByPartidoId(partidoId);

		for (Pronostico p : pronosticos) {
			int puntos = 0;
			boolean acertoLocal = p.getGolesLocal().equals(resultadoRealLocal);
			boolean acertoVisitante = p.getGolesVisitante().equals(resultadoRealVisitante);

			int difReal = resultadoRealLocal - resultadoRealVisitante;
			int difPronostico = p.getGolesLocal() - p.getGolesVisitante();

			if (acertoLocal && acertoVisitante) {
				puntos = 3;
			} else if ((difReal > 0 && difPronostico > 0) || (difReal < 0 && difPronostico < 0)
					|| (difReal == 0 && difPronostico == 0)) {
				puntos = 1;
			}

			p.setPuntosObtenidos(puntos);
			pronosticoRepo.save(p);
		}
	}

	public List<Pronostico> obtenerMisPronosticos(Long pollaId, Authentication auth) {
		return pronosticoRepo.findByUsuarioUsernameAndPollaId(auth.getName(), pollaId);
	}

	public List<UsuarioRankingDTO> obtenerRanking(Long pollaId) {
		Polla polla = pollaRepo.findById(pollaId).orElseThrow();
		List<UsuarioRankingDTO> ranking = new java.util.ArrayList<>();

		for (Usuario u : polla.getParticipantes()) {
			List<Pronostico> pronosticos = pronosticoRepo.findByUsuarioUsernameAndPollaId(u.getUsername(), pollaId);

			int puntosTotales = 0;
			for (Pronostico p : pronosticos) {
				if (p.getPuntosObtenidos() != null) {
					puntosTotales += p.getPuntosObtenidos();
				}
			}
			ranking.add(new UsuarioRankingDTO(u.getNombre(), u.getUsername(), puntosTotales));
		}

		ranking.sort((a, b) -> b.getPuntosTotales().compareTo(a.getPuntosTotales()));
		return ranking;
	}
}