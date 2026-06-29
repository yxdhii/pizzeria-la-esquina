package com.laesquina.pizzeria.dto;

import java.time.LocalDateTime;

/**
 * Objeto de transferencia de datos (DTO) utilizado para representar
 * la información resumida de las ventas generadas por el sistema,
 * incluyendo la factura, la fecha, el cliente, el método de pago
 * y el importe total.
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
