package com.laesquina.pizzeria.exception;

/**
 * Flujo alternativo documentado en el caso de uso "Preparar Pedido":
 * "Falta insumo: Notificar Faltante de Insumo y marcar pedido en espera".
 * Tambien aplica cuando un producto no esta disponible (RF: validar
 * disponibilidad, incluido en "Registrar Pedido" via <<Include>>).
 */
public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
