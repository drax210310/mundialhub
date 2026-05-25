package com.mundial.hub.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mundial.hub.model.Partido;
import com.mundial.hub.repository.PartidoRepository;

@Service
public class PartidoService {

	@Autowired
	private PartidoRepository repo;

	public List<Partido> obtenerPartidos() {
		return repo.findAll();
	}

	public Partido guardar(Partido p) {
		return repo.save(p);
	}
}