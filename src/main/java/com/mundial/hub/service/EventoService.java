package com.mundial.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mundial.hub.model.Evento;
import com.mundial.hub.repository.EventoRepository;

@Service
public class EventoService {

	@Autowired
	private EventoRepository repo;

	public void registrar(String tipo, String descripcion) {
		repo.save(new Evento(tipo, descripcion));
	}
}