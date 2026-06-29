package com.laesquina.pizzeria.exception;

/**
 * Excepción utilizada para indicar que el recurso solicitado
 * no fue encontrado en el sistema.
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
