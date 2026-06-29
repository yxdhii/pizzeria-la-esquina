package com.laesquina.pizzeria.repository;

import com.laesquina.pizzeria.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}
