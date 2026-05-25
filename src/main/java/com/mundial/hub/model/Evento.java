package com.mundial.hub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String tipo;
	private String descripcion;
	private LocalDateTime fecha;

	public Evento() {
	}

	public Evento(String tipo, String descripcion) {
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.fecha = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}
}