package com.laesquina.pizzeria.repository;

import com.laesquina.pizzeria.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Optional<Inventario> findByInsumo_IdInsumo(Long idInsumo);

    // Recupera los registros de inventario cuyo stock actual
    // es igual o inferior al stock mínimo establecido.
    @org.springframework.data.jpa.repository.Query(
        "SELECT i FROM Inventario i WHERE i.stockActual <= i.stockMinimo")
    List<Inventario> findStockBajoMinimo();
}
