package com.laesquina.pizzeria.dto;

import java.time.LocalDateTime;

/**
 * RF-005: "Generar reportes de ventas e inventario". Este DTO es una fila
 * resumida del reporte de ventas (no se expone la entidad Factura completa
 * a la vista para mantener el reporte simple y enfocado).
 */
public class ReporteVentasDTO {

    private Long idFactura;
    private LocalDateTime fecha;
    private String cliente;
    private String metodoPago;
    private Double total;

    public ReporteVentasDTO(Long idFactura, LocalDateTime fecha, String cliente, String metodoPago, Double total) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.metodoPago = metodoPago;
        this.total = total;
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public Double getTotal() {
        return total;
    }
}
