package com.mundial.hub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pronostico")
public class Pronostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer golesLocal;
    private Integer golesVisitante;
    
    // Se llena cuando el partido termina
    private Integer puntosObtenidos; 

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "partido_id")
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "polla_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Polla polla;

    public Pronostico() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getGolesLocal() { return golesLocal; }
    public void setGolesLocal(Integer golesLocal) { this.golesLocal = golesLocal; }
    public Integer getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(Integer golesVisitante) { this.golesVisitante = golesVisitante; }
    public Integer getPuntosObtenidos() { return puntosObtenidos; }
    public void setPuntosObtenidos(Integer puntosObtenidos) { this.puntosObtenidos = puntosObtenidos; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Partido getPartido() { return partido; }
    public void setPartido(Partido partido) { this.partido = partido; }
    public Polla getPolla() { return polla; }
    public void setPolla(Polla polla) { this.polla = polla; }
}
