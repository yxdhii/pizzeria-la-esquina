package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.model.Insumo;
import java.util.List;

public interface InsumoService {
    List<Insumo> listarTodos();
    Insumo buscarPorId(Long id);
    // Recibe stockInicial/stockMinimo porque al crear un Insumo nuevo
    // (RF-0010: registrar proveedores y entradas de insumos) tambien debe
    // crearse su registro de Inventario asociado (relacion 1 a 1 obligatoria).
    Insumo guardar(Insumo insumo, Double stockInicial, Double stockMinimo);
    void eliminar(Long id);
}
