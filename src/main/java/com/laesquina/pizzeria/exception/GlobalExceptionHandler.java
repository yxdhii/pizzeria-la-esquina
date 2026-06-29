package com.laesquina.pizzeria.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Centraliza el manejo de errores para todos los controladores (en vez de
 * repetir try/catch en cada metodo). Redirige a una vista de error generica
 * con un mensaje entendible para el usuario final (mozo/cajero/admin).
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

    // Cubre reglas de negocio como "no se puede cancelar un pedido entregado"
    // o "el pedido ya tiene un comprobante generado" (PedidoServiceImpl, FacturaServiceImpl).
    @ExceptionHandler(IllegalStateException.class)
    public String manejarEstadoInvalido(IllegalStateException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    // Cubre validaciones manuales como "debe seleccionar una mesa" (PedidoServiceImpl).
    @ExceptionHandler(IllegalArgumentException.class)
    public String manejarArgumentoInvalido(IllegalArgumentException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }
}
