package com.laesquina.pizzeria.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    @OneToOne
    @JoinColumn(name = "id_insumo", nullable = false, unique = true)
    private Insumo insumo;

    @Column(nullable = false)
    private Double stockActual = 0.0;

    @Column(nullable = false)
    private Double stockMinimo = 0.0;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    public Inventario() {
    }

    public Long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Double getStockActual() {
        return stockActual;
    }

    public void setStockActual(Double stockActual) {
        this.stockActual = stockActual;
    }

    public Double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

   
    @Transient
    public boolean isStockBajo() {
        return stockActual != null && stockMinimo != null && stockActual <= stockMinimo;
    }
}
