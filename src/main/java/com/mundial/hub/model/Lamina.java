package com.mundial.hub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lamina")
public class Lamina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombreJugador;
	private String seleccion;
	private String imagenUrl;

	private Integer valorMercado = 3;

	public Lamina() {
	}

	public Lamina(String nombreJugador, String seleccion, String imagenUrl, Integer valorMercado) {
		this.nombreJugador = nombreJugador;
		this.seleccion = seleccion;
		this.imagenUrl = imagenUrl;
		this.valorMercado = valorMercado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}

	public String getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(String seleccion) {
		this.seleccion = seleccion;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	public Integer getValorMercado() {
		return valorMercado;
	}

	public void setValorMercado(Integer valorMercado) {
		this.valorMercado = valorMercado;
	}
}