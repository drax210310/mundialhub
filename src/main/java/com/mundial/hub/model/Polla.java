package com.mundial.hub.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polla")
public class Polla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String codigoInvitacion;

    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Usuario creador;

    @ManyToMany
    @JoinTable(
        name = "polla_usuarios",
        joinColumns = @JoinColumn(name = "polla_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> participantes = new ArrayList<>();

    public Polla() {}

    public Polla(String nombre, String codigoInvitacion, Usuario creador) {
        this.nombre = nombre;
        this.codigoInvitacion = codigoInvitacion;
        this.creador = creador;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigoInvitacion() { return codigoInvitacion; }
    public void setCodigoInvitacion(String codigoInvitacion) { this.codigoInvitacion = codigoInvitacion; }
    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = creador; }
    public List<Usuario> getParticipantes() { return participantes; }
    public void setParticipantes(List<Usuario> participantes) { this.participantes = participantes; }
}
