package com.laesquina.pizzeria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de transferencia de datos (DTO) que almacena la información
 * necesaria para registrar cada producto incluido en un pedido,
 * como el producto seleccionado, la cantidad y las observaciones.
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
