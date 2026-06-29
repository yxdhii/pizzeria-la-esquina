package com.laesquina.pizzeria.model.enums;

/**
 * Enumeración que representa las etapas del proceso de atención
 * de un pedido, desde su registro hasta su entrega o cancelación.
 */
public enum EstadoPedido {
    PENDIENTE,
    EN_PREPARACION,
    LISTO,
    ENTREGADO,
    CANCELADO
}
