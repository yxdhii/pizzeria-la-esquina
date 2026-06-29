package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.model.Mesa;
import java.util.List;

public interface MesaService {
    List<Mesa> listarTodas();
    Mesa buscarPorId(Long id);
    Mesa guardar(Mesa mesa);
    void eliminar(Long id);
}
