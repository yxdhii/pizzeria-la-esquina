package com.laesquina.pizzeria.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador global de excepciones de la aplicación.
 *
 * Centraliza el tratamiento de los errores generados por los controladores,
 * mostrando mensajes informativos al usuario mediante una vista de error.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public String manejarRecursoNoEncontrado(RecursoNoEncontradoException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public String manejarStockInsuficiente(StockInsuficienteException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    // Gestiona las excepciones relacionadas con reglas de negocio
    // que impiden la ejecución de una operación.
    @ExceptionHandler(IllegalStateException.class)
    public String manejarEstadoInvalido(IllegalStateException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    // Gestiona las excepciones ocasionadas por datos de entrada
    // inválidos o incompletos durante el procesamiento de solicitudes.
    @ExceptionHandler(IllegalArgumentException.class)
    public String manejarArgumentoInvalido(IllegalArgumentException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }
}
