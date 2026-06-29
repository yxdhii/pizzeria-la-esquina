package com.laesquina.pizzeria.repository;

import com.laesquina.pizzeria.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    // reportes de ventas por rango de fechas.
    List<Factura> findByFechaEmisionBetween(LocalDateTime desde, LocalDateTime hasta);
}
