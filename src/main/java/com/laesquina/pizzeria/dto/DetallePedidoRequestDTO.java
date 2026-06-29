package com.laesquina.pizzeria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para una linea del formulario "Registrar Pedido". Se usa un DTO en
 * lugar de la entidad DetallePedido directamente en el formulario porque el
 * formulario solo necesita idProducto/cantidad/notas; el precioUnitario y la
 * relacion con Pedido los completa el servicio, no el usuario.
 */
public class DetallePedidoRequestDTO {

    @NotNull(message = "Debe seleccionar un producto")
    private Long idProducto;

    @NotNull
    @Min(value = 1, message = "La cantidad minima es 1")
    private Integer cantidad;

    private String notas;

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
