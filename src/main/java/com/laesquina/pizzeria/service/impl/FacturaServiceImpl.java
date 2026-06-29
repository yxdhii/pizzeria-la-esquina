package com.laesquina.pizzeria.service.impl;

import com.laesquina.pizzeria.exception.RecursoNoEncontradoException;
import com.laesquina.pizzeria.model.Factura;
import com.laesquina.pizzeria.model.Pedido;
import com.laesquina.pizzeria.model.enums.EstadoPedido;
import com.laesquina.pizzeria.model.enums.MetodoPago;
import com.laesquina.pizzeria.repository.FacturaRepository;
import com.laesquina.pizzeria.repository.PedidoRepository;
import com.laesquina.pizzeria.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Caso de uso "Cobrar / Registrar Venta" (seccion 3.2.c): "Sistema calcula
 * total... Generar Comprobante (boleta/factura)". El pedido debe estar en
 * estado LISTO o ENTREGADO segun el flujo (pago en mesa se cobra antes de
 * liberar; pago en caja se cobra al momento de entregar), por eso aqui solo
 * se exige que no este ya facturado ni cancelado.
 */
@Service
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public FacturaServiceImpl(FacturaRepository facturaRepository, PedidoRepository pedidoRepository) {
        this.facturaRepository = facturaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public List<Factura> listarTodas() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura buscarPorId(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Factura no encontrada con id " + id));
    }

    @Override
    @Transactional
    public Factura generarFactura(Long idPedido, MetodoPago metodoPago) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con id " + idPedido));

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new IllegalStateException("No se puede facturar un pedido cancelado");
        }
        if (pedido.getFactura() != null) {
            throw new IllegalStateException("El pedido " + idPedido + " ya tiene un comprobante generado");
        }

        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setMontoTotal(pedido.getTotal());
        factura.setMetodoPago(metodoPago);

        Factura guardada = facturaRepository.save(factura);
        pedido.setFactura(guardada);
        pedidoRepository.save(pedido);
        return guardada;
    }
}
