package com.mundial.hub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entrada")
public class Entrada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private EstadoEntrada estado;

	private LocalDateTime fechaReserva;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "partido_id")
	private Partido partido;

	public Entrada() {
	}

	public Entrada(Partido partido) {

		this.partido = partido;
		this.estado = EstadoEntrada.DISPONIBLE;
	}

	public Long getId() {
		return id;
	}

	public EstadoEntrada getEstado() {
		return estado;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEstado(EstadoEntrada estado) {
		this.estado = estado;
	}

	public void setFechaReserva(LocalDateTime fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}
}