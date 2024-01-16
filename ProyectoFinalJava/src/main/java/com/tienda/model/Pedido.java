package com.tienda.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private LocalDate fechaPedido;
    private Double montoTotal;
    private Boolean despacho;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "pedido_producto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private Set<Producto> productos;

    public Pedido() {

    }

    public Pedido(LocalDate fechaPedido, Double montoTotal, Boolean despacho) {
        this.fechaPedido = fechaPedido;
        this.montoTotal = montoTotal;
        this.despacho = despacho;
    }

    public Pedido(LocalDate fechaPedido, Double montoTotal, Boolean despacho, Usuario usuario) {
        this.fechaPedido = fechaPedido;
        this.montoTotal = montoTotal;
        this.despacho = despacho;
        this.usuario = usuario;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Boolean getDespacho() {
        return despacho;
    }

    public void setDespacho(Boolean despacho) {
        this.despacho = despacho;
    }
}
