package com.mundial.hub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "oferta_market")
public class OfertaMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quién la vende
    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    // Qué lámina es
    @ManyToOne
    @JoinColumn(name = "lamina_id")
    private Lamina lamina;

    // A qué precio la vende el usuario
    private Integer precio;

    private Boolean activa = true;

    public OfertaMarket() {}

    public OfertaMarket(Usuario vendedor, Lamina lamina, Integer precio) {
        this.vendedor = vendedor;
        this.lamina = lamina;
        this.precio = precio;
        this.activa = true;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getVendedor() { return vendedor; }
    public void setVendedor(Usuario vendedor) { this.vendedor = vendedor; }
    public Lamina getLamina() { return lamina; }
    public void setLamina(Lamina lamina) { this.lamina = lamina; }
    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}