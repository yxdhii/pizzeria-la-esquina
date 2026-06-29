package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.model.Inventario;
import java.util.List;

public interface InventarioService {
    List<Inventario> listarTodos();
    List<Inventario> listarStockBajoMinimo();
    Inventario buscarPorId(Long id);

    /** registrar entrada de insumos (compra a proveedor). */
    Inventario registrarEntrada(Long idInsumo, Double cantidad);

    /** Salida manual de insumos (merma, uso fuera del flujo automatico de pedidos). */
    Inventario registrarSalida(Long idInsumo, Double cantidad);

    void actualizarStockMinimo(Long idInventario, Double nuevoMinimo);
}
