package com.laesquina.pizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

/**
 * Entidad que representa el detalle de un pedido,
 * almacenando los productos solicitados, la cantidad,
 * el precio unitario y las observaciones asociadas.
 */
@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad;

    // Conserva el precio unitario registrado al momento del pedido.
    @Column(nullable = false)
    private Double precioUnitario;

    @Column(length = 200)
    private String notas;

    public DetallePedido() {
    }

    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    @Transient
    public Double getSubtotal() {
        if (precioUnitario == null || cantidad == null) return 0.0;
        return precioUnitario * cantidad;
    }
}
