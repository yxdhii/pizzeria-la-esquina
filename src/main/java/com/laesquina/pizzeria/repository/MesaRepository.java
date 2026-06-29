package com.laesquina.pizzeria.repository;

import com.laesquina.pizzeria.model.Mesa;
import com.laesquina.pizzeria.model.enums.EstadoMesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

    List<Mesa> findByEstado(EstadoMesa estado);
}
