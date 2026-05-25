package com.mundial.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mundial.hub.model.Evento;
import com.mundial.hub.repository.EventoRepository;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

	@Autowired
	private EventoRepository repo;

	@GetMapping
	public List<Evento> listar() {
		return repo.findAll();
	}
}