package com.mundial.hub.model;

import jakarta.persistence.*;

@Entity
public class Notificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mensaje;
	private String tipo; // INFO, ALERTA

	public Notificacion() {
	}

	public Notificacion(String mensaje, String tipo) {
		this.mensaje = mensaje;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getTipo() {
		return tipo;
	}
}
