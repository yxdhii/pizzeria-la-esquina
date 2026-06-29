package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.model.Empleado;
import java.util.List;

public interface EmpleadoService {
    List<Empleado> listarTodos();
    Empleado buscarPorId(Long id);
    Empleado guardar(Empleado empleado);
    void eliminar(Long id);
}
