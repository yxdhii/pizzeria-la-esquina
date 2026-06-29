package com.laesquina.pizzeria.service.impl;

import com.laesquina.pizzeria.dto.ReporteVentasDTO;
import com.laesquina.pizzeria.model.Factura;
import com.laesquina.pizzeria.model.Inventario;
import com.laesquina.pizzeria.repository.FacturaRepository;
import com.laesquina.pizzeria.repository.InventarioRepository;
import com.laesquina.pizzeria.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/** RF-005: "Generar reportes automaticos de ventas e inventarios". */
@Service
public class ReporteServiceImpl implements ReporteService {

    private final FacturaRepository facturaRepository;
    private final InventarioRepository inventarioRepository;

    @Autowired
    public ReporteServiceImpl(FacturaRepository facturaRepository, InventarioRepository inventarioRepository) {
        this.facturaRepository = facturaRepository;
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public List<ReporteVentasDTO> reporteVentas(LocalDate desde, LocalDate hasta) {
        // .atStartOfDay() / hasta + 1 dia para incluir todas las horas del dia "hasta".
        LocalDateTime inicio = desde.atStartOfDay();
        LocalDateTime fin = hasta.plusDays(1).atStartOfDay();

        List<Factura> facturas = facturaRepository.findByFechaEmisionBetween(inicio, fin);
        return facturas.stream()
                .map(f -> new ReporteVentasDTO(
                        f.getIdFactura(),
                        f.getFechaEmision(),
                        f.getPedido().getCliente().getNombreCompleto(),
                        f.getMetodoPago().name(),
                        f.getMontoTotal()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Inventario> reporteInventarioBajoMinimo() {
        return inventarioRepository.findStockBajoMinimo();
    }
}
