package com.mundial.hub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mundial.hub.model.Partido;
import com.mundial.hub.repository.PartidoRepository;

@Service
public class PartidoSyncService {

	private static final Logger logger = LoggerFactory.getLogger(PartidoSyncService.class);

	@Autowired
	private PartidoRepository partidoRepo;

	@Autowired
	private EventoService eventoService;

	public static class PartidoApiDTO {
		public String local;
		public String visitante;
		public String fecha;
	}

	public void sincronizarConAPI(boolean forzarFalla) {
		logger.info("Iniciando proceso de sincronización externa...");
		RestTemplate restTemplate = new RestTemplate();

		String apiUrl = forzarFalla ? "https://api-inexistente-mundial.com/error"
				: "https://mocki.io/v1/7db6402a-efcb-4ec1-ac06-1c0c50b0e409";

		try {
			PartidoApiDTO[] partidosExternos = restTemplate.getForObject(apiUrl, PartidoApiDTO[].class);

			if (partidosExternos != null && partidosExternos.length > 0) {
				for (PartidoApiDTO dto : partidosExternos) {
					partidoRepo.save(new Partido(dto.local, dto.visitante, dto.fecha));
				}
				logger.info("Sincronización exitosa con proveedor externo.");
				eventoService.registrar("SINCRONIZACION_EXITOSA",
						"Se descargaron los partidos de la API externa correctamente.");
			}

		} catch (Exception e) {
			logger.warn("Falla detectada en el proveedor. Activando plan de contingencia.");
			eventoService.registrar("ALERTA_API_EXTERNA",
					"Falla de conexión con API. Se activó el plan de contingencia.");

			if (partidoRepo.count() == 0) {
				partidoRepo.save(new Partido("México", "Polonia", "2026-06-15T10:00"));
				partidoRepo.save(new Partido("Alemania", "Japón", "2026-06-16T14:00"));
				partidoRepo.save(new Partido("Uruguay", "Corea del Sur", "2026-06-17T18:00"));
			}
		}
	}
}