package com.laesquina.pizzeria.service.impl;

import com.laesquina.pizzeria.dto.DetallePedidoRequestDTO;
import com.laesquina.pizzeria.dto.PedidoRequestDTO;
import com.laesquina.pizzeria.exception.RecursoNoEncontradoException;
import com.laesquina.pizzeria.exception.StockInsuficienteException;
import com.laesquina.pizzeria.model.*;
import com.laesquina.pizzeria.model.enums.EstadoMesa;
import com.laesquina.pizzeria.model.enums.EstadoPedido;
import com.laesquina.pizzeria.model.enums.TipoPedido;
import com.laesquina.pizzeria.repository.*;
import com.laesquina.pizzeria.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementa los casos de uso "Registrar Pedido", "Preparar Pedido" y
 * "Entregar Pedido" (secciones 3.2.a, 3.2.b y 3.2.d del documento), junto con
 * sus flujos alternativos: cancelacion antes de confirmar y validacion de
 * disponibilidad (<<Include>> "Validar disponibilidad" del diagrama de
 * casos de uso, Ilustracion 7).
 */
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final MesaRepository mesaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                              ClienteRepository clienteRepository,
                              MesaRepository mesaRepository,
                              EmpleadoRepository empleadoRepository,
                              ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.mesaRepository = mesaRepository;
        this.empleadoRepository = empleadoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public List<Pedido> listarPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstadoOrderByFechaHoraAsc(estado);
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con id " + id));
    }

    @Override
    @Transactional
    public Pedido registrarPedido(PedidoRequestDTO dto) {
        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos un producto");
        }

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con id " + dto.getIdCliente()));

        Empleado empleado = empleadoRepository.findById(dto.getIdEmpleado())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado con id " + dto.getIdEmpleado()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEmpleado(empleado);
        pedido.setTipoPedido(dto.getTipoPedido());
        pedido.setEstado(EstadoPedido.PENDIENTE);

        // Regla de negocio: la mesa solo aplica cuando el pedido es en salon
        // (ver Ilustracion 7: "Asignar mesa" es exclusivo del flujo de Mozo en salon).
        if (dto.getTipoPedido() == TipoPedido.SALON) {
            if (dto.getIdMesa() == null) {
                throw new IllegalArgumentException("Debe seleccionar una mesa para un pedido en salon");
            }
            Mesa mesa = mesaRepository.findById(dto.getIdMesa())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Mesa no encontrada con id " + dto.getIdMesa()));
            mesa.setEstado(EstadoMesa.OCUPADA);
            mesaRepository.save(mesa);
            pedido.setMesa(mesa);
        }

        double total = 0.0;
        for (DetallePedidoRequestDTO detalleDTO : dto.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id " + detalleDTO.getIdProducto()));

            // Caso de uso "Validar disponibilidad" (<<Include>> de Registrar Pedido).
            if (!Boolean.TRUE.equals(producto.getDisponibilidad())) {
                throw new StockInsuficienteException("El producto '" + producto.getNombreProducto() + "' no esta disponible");
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setNotas(detalleDTO.getNotas());

            pedido.agregarDetalle(detalle);
            total += detalle.getSubtotal();
        }

        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido iniciarPreparacion(Long idPedido) {
        Pedido pedido = buscarPorId(idPedido);
        validarTransicion(pedido, EstadoPedido.PENDIENTE, "iniciar la preparacion");
        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido marcarListo(Long idPedido) {
        Pedido pedido = buscarPorId(idPedido);
        validarTransicion(pedido, EstadoPedido.EN_PREPARACION, "marcar como listo");
        pedido.setEstado(EstadoPedido.LISTO);
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido marcarEntregado(Long idPedido) {
        Pedido pedido = buscarPorId(idPedido);
        validarTransicion(pedido, EstadoPedido.LISTO, "marcar como entregado");
        pedido.setEstado(EstadoPedido.ENTREGADO);
        liberarMesaSiAplica(pedido);
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido cancelarPedido(Long idPedido) {
        Pedido pedido = buscarPorId(idPedido);
        if (pedido.getEstado() == EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("No se puede cancelar un pedido ya entregado");
        }
        pedido.setEstado(EstadoPedido.CANCELADO);
        liberarMesaSiAplica(pedido);
        return pedidoRepository.save(pedido);
    }

    private void validarTransicion(Pedido pedido, EstadoPedido estadoEsperado, String accion) {
        if (pedido.getEstado() != estadoEsperado) {
            throw new IllegalStateException(
                    "No se puede " + accion + ": el pedido esta en estado " + pedido.getEstado());
        }
    }

    private void liberarMesaSiAplica(Pedido pedido) {
        if (pedido.getMesa() != null) {
            Mesa mesa = pedido.getMesa();
            mesa.setEstado(EstadoMesa.DISPONIBLE);
            mesaRepository.save(mesa);
        }
    }
}
