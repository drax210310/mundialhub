package com.mundial.hub.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@Column(unique = true)
	private String username;

	private String password;

	@Column(unique = true)
	private String email;

	private String rol;

	private Integer monedasCambio = 0;

	private String equipoFavorito;
	private String sedeFavorita;

	@OneToMany(mappedBy = "usuario")
	@JsonIgnore
	private List<Entrada> entradas;

	@ManyToMany
	@JoinTable(name = "usuario_laminas", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "lamina_id"))
	private List<Lamina> laminas;

	@ManyToMany
	@JoinTable(name = "usuario_repetidas", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "lamina_id"))
	private List<Lamina> repetidas = new java.util.ArrayList<>();

	public List<Lamina> getRepetidas() {
		return repetidas;
	}

	public void setRepetidas(List<Lamina> repetidas) {
		this.repetidas = repetidas;
	}

	public Usuario() {
	}

	public Usuario(String nombre, String username, String password, String email, String rol) {
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		this.email = email;
		this.rol = rol;
		this.monedasCambio = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Integer getMonedasCambio() {
		return monedasCambio;
	}

	public void setMonedasCambio(Integer monedasCambio) {
		this.monedasCambio = monedasCambio;
	}

	public String getEquipoFavorito() {
		return equipoFavorito;
	}

	public void setEquipoFavorito(String equipoFavorito) {
		this.equipoFavorito = equipoFavorito;
	}

	public String getSedeFavorita() {
		return sedeFavorita;
	}

	public void setSedeFavorita(String sedeFavorita) {
		this.sedeFavorita = sedeFavorita;
	}

	public List<Entrada> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<Entrada> entradas) {
		this.entradas = entradas;
	}

	public List<Lamina> getLaminas() {
		return laminas;
	}

	public void setLaminas(List<Lamina> laminas) {
		this.laminas = laminas;
	}
}