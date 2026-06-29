package com.laesquina.pizzeria.repository;

import com.laesquina.pizzeria.model.Pedido;
import com.laesquina.pizzeria.model.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByEstadoOrderByFechaHoraAsc(EstadoPedido estado);
}
