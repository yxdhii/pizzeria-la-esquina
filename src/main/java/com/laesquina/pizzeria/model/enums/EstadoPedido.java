package com.laesquina.pizzeria.model.enums;

/**
 * Tabla 3 (Pedido.estado) + RF-008: estado del pedido (pendiente, en preparacion,
 * listo, entregado). Se agrega CANCELADO como flujo alternativo documentado
 * en las especificaciones de caso de uso ("Cliente cancela antes de confirmar").
 */
public enum EstadoPedido {
    PENDIENTE,
    EN_PREPARACION,
    LISTO,
    ENTREGADO,
    CANCELADO
}
