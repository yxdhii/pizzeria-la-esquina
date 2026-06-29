package com.laesquina.pizzeria.exception;

/**
 * Excepción lanzada cuando la cantidad disponible de un recurso
 * es insuficiente para completar una operación, como el registro
 * o la preparación de un pedido.
 */
public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
