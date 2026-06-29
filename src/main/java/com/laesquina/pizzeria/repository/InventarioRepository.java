package com.laesquina.pizzeria.repository;

import com.laesquina.pizzeria.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Optional<Inventario> findByInsumo_IdInsumo(Long idInsumo);

    // RF-009: alertas de stock minimo. Se filtra en la consulta en lugar de
    // traer todo y filtrar en Java, para que escale bien con muchos insumos.
    @org.springframework.data.jpa.repository.Query(
        "SELECT i FROM Inventario i WHERE i.stockActual <= i.stockMinimo")
    List<Inventario> findStockBajoMinimo();
}
