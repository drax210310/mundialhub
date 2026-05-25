package com.mundial.hub.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "partido")
public class Partido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String local;
	private String visitante;
	private String fecha;

	// 🔥 NUEVOS CAMPOS: Para saber si ya terminó y cuánto quedaron
	private String estado = "PROGRAMADO"; // Puede ser PROGRAMADO o FINALIZADO
	private Integer golesLocal;
	private Integer golesVisitante;

	@OneToMany(mappedBy = "partido")
	@JsonIgnore
	private List<Entrada> entradas;

	public Partido() {
	}

	public Partido(String local, String visitante, String fecha) {
		this.local = local;
		this.visitante = visitante;
		this.fecha = fecha;
		this.estado = "PROGRAMADO";
	}

	// =====================================
	// GETTERS Y SETTERS
	// =====================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getVisitante() {
		return visitante;
	}

	public void setVisitante(String visitante) {
		this.visitante = visitante;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getGolesLocal() {
		return golesLocal;
	}

	public void setGolesLocal(Integer golesLocal) {
		this.golesLocal = golesLocal;
	}

	public Integer getGolesVisitante() {
		return golesVisitante;
	}

	public void setGolesVisitante(Integer golesVisitante) {
		this.golesVisitante = golesVisitante;
	}

	public List<Entrada> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<Entrada> entradas) {
		this.entradas = entradas;
	}
}