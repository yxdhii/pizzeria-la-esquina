package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.dto.PedidoRequestDTO;
import com.laesquina.pizzeria.model.Pedido;
import com.laesquina.pizzeria.model.enums.EstadoPedido;
import java.util.List;

public interface PedidoService {

    List<Pedido> listarTodos();
    List<Pedido> listarPorEstado(EstadoPedido estado);
    Pedido buscarPorId(Long id);

    /** Registra un nuevo pedido en el sistema.*/
    Pedido registrarPedido(PedidoRequestDTO dto);

    /** Inicia el proceso de preparación de un pedido. */
    Pedido iniciarPreparacion(Long idPedido);

    /**  Actualiza el estado del pedido a listo para su entrega. */
    Pedido marcarListo(Long idPedido);

    /** Marca un pedido como entregado. */
    Pedido marcarEntregado(Long idPedido);

    /** Cancela un pedido registrado en el sistema. */
    Pedido cancelarPedido(Long idPedido);
}
