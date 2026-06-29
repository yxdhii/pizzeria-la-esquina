package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.model.Factura;
import com.laesquina.pizzeria.model.enums.MetodoPago;
import java.util.List;

public interface FacturaService {
    List<Factura> listarTodas();
    Factura buscarPorId(Long id);

    /** "Cobrar / Registrar Venta". */
    Factura generarFactura(Long idPedido, MetodoPago metodoPago);
}

