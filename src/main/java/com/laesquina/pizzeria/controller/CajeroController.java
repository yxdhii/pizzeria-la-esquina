package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.model.Factura;
import com.laesquina.pizzeria.model.enums.EstadoPedido;
import com.laesquina.pizzeria.model.enums.MetodoPago;
import com.laesquina.pizzeria.service.FacturaService;
import com.laesquina.pizzeria.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/** Caso de uso "Cobrar / Registrar Venta" (3.2.c), a cargo del Cajero. */
@Controller
@RequestMapping("/cajero")
public class CajeroController {

    private final PedidoService pedidoService;
    private final FacturaService facturaService;

    @Autowired
    public CajeroController(PedidoService pedidoService, FacturaService facturaService) {
        this.pedidoService = pedidoService;
        this.facturaService = facturaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("porCobrar", pedidoService.listarPorEstado(EstadoPedido.LISTO).size());
        return "cajero/dashboard";
    }

    @GetMapping("/pedidos")
    public String listarPedidos(Model model) {
        model.addAttribute("activePage", "porCobrar");
        model.addAttribute("pedidos", pedidoService.listarPorEstado(EstadoPedido.LISTO));
        return "cajero/pedidos";
    }

    @PostMapping("/pedidos/{id}/cobrar")
    public String cobrar(@PathVariable Long id, @RequestParam MetodoPago metodoPago) {
        facturaService.generarFactura(id, metodoPago);
        return "redirect:/cajero/pedidos";
    }

    @GetMapping("/facturas")
    public String listarFacturas(Model model) {
        model.addAttribute("activePage", "comprobantes");
        model.addAttribute("facturas", facturaService.listarTodas());
        return "cajero/facturas";
    }

    @GetMapping("/facturas/{id}")
    public String verFactura(@PathVariable Long id, Model model) {
        Factura factura = facturaService.buscarPorId(id);
        model.addAttribute("activePage", "comprobantes");
        model.addAttribute("factura", factura);
        return "cajero/factura-detalle";
    }
}
