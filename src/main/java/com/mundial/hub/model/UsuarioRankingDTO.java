package com.mundial.hub.model;

public class UsuarioRankingDTO {
	private String nombre;
	private String username;
	private Integer puntosTotales;

	public UsuarioRankingDTO(String nombre, String username, Integer puntosTotales) {
		this.nombre = nombre;
		this.username = username;
		this.puntosTotales = puntosTotales;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPuntosTotales() {
		return puntosTotales;
	}

	public void setPuntosTotales(Integer puntosTotales) {
		this.puntosTotales = puntosTotales;
	}
}