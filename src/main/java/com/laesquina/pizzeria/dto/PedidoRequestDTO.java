package com.laesquina.pizzeria.dto;

import com.laesquina.pizzeria.model.enums.TipoPedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de transferencia de datos (DTO) utilizado para almacenar
 * la información necesaria durante el registro de un pedido,
 * incluyendo el cliente, el tipo de pedido, la mesa asignada,
 * el empleado responsable y el detalle de los productos solicitados.
 */
public class PedidoRequestDTO {

    @NotNull(message = "Debe seleccionar un cliente")
    private Long idCliente;

    @NotNull(message = "Debe indicar el tipo de pedido")
    private TipoPedido tipoPedido;

    // Campo requerido únicamente para pedidos en salón.
    // Su validación se realiza en la capa de servicios.
    private Long idMesa;

    //@NotNull(message = "Debe indicar el empleado que registra el pedido")
    private Long idEmpleado;

    @Valid
    private List<DetallePedidoRequestDTO> detalles = new ArrayList<>();

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public List<DetallePedidoRequestDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoRequestDTO> detalles) {
        this.detalles = detalles;
    }
}
