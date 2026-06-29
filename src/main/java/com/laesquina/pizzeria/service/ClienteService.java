package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.model.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> listarTodos();
    Cliente buscarPorId(Long id);
    Cliente guardar(Cliente cliente);
    void eliminar(Long id);
}
