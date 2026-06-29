package com.laesquina.pizzeria.repository;

import com.laesquina.pizzeria.model.Empleado;
import com.laesquina.pizzeria.model.enums.RolEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByUsuario(String usuario);

    List<Empleado> findByRol(RolEmpleado rol);
}
