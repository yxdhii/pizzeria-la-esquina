package com.laesquina.pizzeria.exception;

/** Se lanza cuando se busca por id un Cliente/Producto/Pedido/etc. que no existe. */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
