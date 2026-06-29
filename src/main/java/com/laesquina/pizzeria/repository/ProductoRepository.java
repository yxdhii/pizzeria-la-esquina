package com.laesquina.pizzeria.repository;

import com.laesquina.pizzeria.model.Producto;
import com.laesquina.pizzeria.model.enums.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByDisponibilidadTrue();

    List<Producto> findByTipoProducto(TipoProducto tipoProducto);
}
