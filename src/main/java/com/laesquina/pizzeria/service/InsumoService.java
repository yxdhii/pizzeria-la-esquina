package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.model.Insumo;
import java.util.List;

public interface InsumoService {
    List<Insumo> listarTodos();
    Insumo buscarPorId(Long id);
    
    Insumo guardar(Insumo insumo, Double stockInicial, Double stockMinimo);
    void eliminar(Long id);
}
