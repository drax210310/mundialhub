package com.mundial.hub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Notificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mensaje;
	private String tipo;
	private LocalDateTime fecha;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonIgnore
	private Usuario usuario;

	public Notificacion() {
	}

	public Notificacion(String mensaje, String tipo, Usuario usuario) {
		this.mensaje = mensaje;
		this.tipo = tipo;
		this.usuario = usuario;
		this.fecha = LocalDateTime.now();
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

	public LocalDateTime getFecha() {
		return fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}
}