package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.model.Proveedor;
import java.util.List;

public interface ProveedorService {
    List<Proveedor> listarTodos();
    Proveedor buscarPorId(Long id);
    Proveedor guardar(Proveedor proveedor);
    void eliminar(Long id);
}
