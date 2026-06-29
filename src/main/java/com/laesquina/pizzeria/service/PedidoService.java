package com.laesquina.pizzeria.service;

import com.laesquina.pizzeria.dto.PedidoRequestDTO;
import com.laesquina.pizzeria.model.Pedido;
import com.laesquina.pizzeria.model.enums.EstadoPedido;
import java.util.List;

public interface PedidoService {

    List<Pedido> listarTodos();
    List<Pedido> listarPorEstado(EstadoPedido estado);
    Pedido buscarPorId(Long id);

    /** Caso de uso "Registrar Pedido": valida disponibilidad y calcula el total. */
    Pedido registrarPedido(PedidoRequestDTO dto);

    /** Caso de uso "Preparar Pedido": pasa de PENDIENTE a EN_PREPARACION. */
    Pedido iniciarPreparacion(Long idPedido);

    /** Caso de uso "Preparar Pedido": marca LISTO y notifica (paso 4-5 del flujo basico). */
    Pedido marcarListo(Long idPedido);

    /** Caso de uso "Entregar Pedido": marca ENTREGADO. */
    Pedido marcarEntregado(Long idPedido);

    /** Flujo alternativo "Cliente cancela antes de confirmar". */
    Pedido cancelarPedido(Long idPedido);
}
