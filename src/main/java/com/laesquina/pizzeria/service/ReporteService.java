package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.dto.ReporteVentasDTO;
import com.laesquina.pizzeria.model.Inventario;

import java.time.LocalDate;
import java.util.List;

/** Genera reportes automaticos de ventas e inventarios". */
public interface ReporteService {

    List<ReporteVentasDTO> reporteVentas(LocalDate desde, LocalDate hasta);

    List<Inventario> reporteInventarioBajoMinimo();
}
