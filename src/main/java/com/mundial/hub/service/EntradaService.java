package com.mundial.hub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.mundial.hub.model.Entrada;
import com.mundial.hub.model.EstadoEntrada;
import com.mundial.hub.model.Partido;
import com.mundial.hub.model.Usuario;
import com.mundial.hub.repository.EntradaRepository;
import com.mundial.hub.repository.PartidoRepository;
import com.mundial.hub.repository.UsuarioRepository;

@Service
public class EntradaService {

	private static final Logger logger = LoggerFactory.getLogger(EntradaService.class);
	@Autowired
	private NotificacionService notificacionService;
	@Autowired
	private EntradaRepository entradaRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private PartidoRepository partidoRepo;
	@Autowired
	private EventoService eventoService;

	public Entrada reservarEntrada(Long partidoId, Authentication auth) {
		Usuario usuario = usuarioRepo.findByUsername(auth.getName()).orElseThrow();
		Partido partido = partidoRepo.findById(partidoId).orElseThrow();
		LocalDateTime ahora = LocalDateTime.now();

		Entrada entradaExistente = entradaRepo.findByUsuarioUsernameAndPartidoId(auth.getName(), partidoId)
				.orElse(null);

		if (entradaExistente != null) {
			if (entradaExistente.getEstado() == EstadoEntrada.PAGADA) {
				logger.warn("Reserva denegada: Usuario '{}' ya tiene pago el partido ID {}", auth.getName(), partidoId);
				throw new RuntimeException("Ya tienes una entrada comprada para este partido.");
			}

			if (entradaExistente.getEstado() == EstadoEntrada.RESERVADA) {
				if (entradaExistente.getFechaReserva().plusMinutes(5).isAfter(ahora)) {
					throw new RuntimeException("Ya tienes una reserva activa. ¡Ve a pagarla antes de que expire!");
				}
			}

			entradaExistente.setEstado(EstadoEntrada.RESERVADA);
			entradaExistente.setFechaReserva(ahora);

			Entrada reciclada = entradaRepo.save(entradaExistente);

			logger.info("Ticket ID {} reciclado para Usuario '{}'", reciclada.getId(), auth.getName());
			eventoService.registrar("RESERVA_CREADA",
					"El usuario " + auth.getName() + " volvió a reservar entrada para el partido " + partido.getLocal()
							+ " vs " + partido.getVisitante());

			return reciclada;
		}

		Entrada entrada = new Entrada();
		entrada.setUsuario(usuario);
		entrada.setPartido(partido);
		entrada.setEstado(EstadoEntrada.RESERVADA);
		entrada.setFechaReserva(ahora);

		Entrada guardada = entradaRepo.save(entrada);

		logger.info("Reserva exitosa: Ticket ID {} creado para Usuario '{}'", guardada.getId(), auth.getName());
		eventoService.registrar("RESERVA_CREADA", "El usuario " + auth.getName() + " reservó entrada para el partido "
				+ partido.getLocal() + " vs " + partido.getVisitante());

		return guardada;
	}

	public List<Entrada> misEntradas(Authentication auth) {
		return entradaRepo.findByUsuarioUsername(auth.getName());
	}

	public Entrada pagarEntrada(Long entradaId, Authentication auth) {
		Entrada entrada = entradaRepo.findById(entradaId).orElseThrow();

		if (!entrada.getUsuario().getUsername().equals(auth.getName())) {
			logger.error("Seguridad: Usuario '{}' intentó pagar el ticket ID {} que no le pertenece", auth.getName(),
					entradaId);
			throw new RuntimeException("No tienes permiso para pagar esta entrada");
		}

		if (entrada.getEstado() != EstadoEntrada.RESERVADA) {
			throw new RuntimeException("La entrada expiró o no está en estado de reserva");
		}

		entrada.setEstado(EstadoEntrada.PAGADA);
		Entrada pagada = entradaRepo.save(entrada);

		logger.info("Pago confirmado: Ticket ID {} pagado por Usuario '{}'", entradaId, auth.getName());
		eventoService.registrar("PAGO_REALIZADO",
				"El usuario " + auth.getName() + " pagó exitosamente la entrada ID " + entradaId);

		return pagada;
	}

	public Entrada transferirEntrada(Long entradaId, String usernameDestino, Authentication auth) {
		Entrada entrada = entradaRepo.findById(entradaId)
				.orElseThrow(() -> new RuntimeException("Entrada no encontrada."));

		if (!entrada.getUsuario().getUsername().equals(auth.getName())) {
			logger.error("Alerta Fraude: Usuario '{}' intentó transferir el ticket {} de otro usuario.", auth.getName(),
					entradaId);
			throw new RuntimeException("No eres el dueño de esta entrada.");
		}

		if (entrada.getEstado() != EstadoEntrada.PAGADA) {
			throw new RuntimeException("Solo puedes transferir entradas que ya estén pagadas.");
		}

		Usuario destinatario = usuarioRepo.findByUsername(usernameDestino)
				.orElseThrow(() -> new RuntimeException("El usuario destino no existe. Verifique el username."));

		if (destinatario.getUsername().equals(auth.getName())) {
			throw new RuntimeException("No puedes transferirte la entrada a ti mismo.");
		}

		entrada.setUsuario(destinatario);
		Entrada transferida = entradaRepo.save(entrada);

		logger.info("Transferencia exitosa: Ticket ID {} pasó de '{}' a '{}'", entradaId, auth.getName(),
				usernameDestino);
		eventoService.registrar("ENTRADA_TRANSFERIDA",
				"El usuario " + auth.getName() + " transfirió la entrada #" + entradaId + " a " + usernameDestino);

		return transferida;
	}

	@Scheduled(fixedRate = 60000)
	public void expirarReservasVencidas() {
		List<Entrada> todas = entradaRepo.findAll();
		LocalDateTime ahora = LocalDateTime.now();

		for (Entrada e : todas) {
			if (e.getEstado() == EstadoEntrada.RESERVADA && e.getFechaReserva() != null) {
				if (e.getFechaReserva().plusMinutes(5).isBefore(ahora)) {
					e.setEstado(EstadoEntrada.EXPIRADA);
					entradaRepo.save(e);

					logger.warn("Expiración TTL: Ticket ID {} expirado automáticamente", e.getId());
					eventoService.registrar("RESERVA_EXPIRADA", "El sistema expiró automáticamente la reserva ID "
							+ e.getId() + " del usuario " + e.getUsuario().getUsername());

					String mensaje = "¡Atención! Tu reserva para el partido " + e.getPartido().getLocal() + " vs "
							+ e.getPartido().getVisitante() + " ha expirado por falta de pago.";
					notificacionService.enviarNotificacion(e.getUsuario(), mensaje, "ALERTA");
				}
			}
		}
	}
}