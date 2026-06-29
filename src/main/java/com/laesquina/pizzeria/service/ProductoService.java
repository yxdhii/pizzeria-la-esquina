package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.model.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> listarTodos();
    List<Producto> listarDisponibles();
    Producto buscarPorId(Long id);
    Producto guardar(Producto producto);
    void eliminar(Long id);
}
