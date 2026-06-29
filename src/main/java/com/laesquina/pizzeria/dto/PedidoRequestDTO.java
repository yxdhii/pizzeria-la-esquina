package com.laesquina.pizzeria.dto;

import com.laesquina.pizzeria.model.enums.TipoPedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso "Registrar Pedido" (seccion 3.2.a del documento):
 * "Ingresa items: producto, cantidad, notas. Selecciona mesa (si salon) o
 * datos de entrega (si delivery)". Este DTO agrupa exactamente esos datos.
 */
public class PedidoRequestDTO {

    @NotNull(message = "Debe seleccionar un cliente")
    private Long idCliente;

    @NotNull(message = "Debe indicar el tipo de pedido")
    private TipoPedido tipoPedido;

    // Solo obligatorio si tipoPedido = SALON; se valida en el servicio
    // porque la obligatoriedad depende de otro campo (no se puede expresar
    // con una sola anotacion declarativa simple).
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
