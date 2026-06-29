package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.model.enums.EstadoPedido;
import com.laesquina.pizzeria.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/** Caso de uso "Preparar Pedido" (3.2.b), a cargo del Cocinero. */
@Controller
@RequestMapping("/cocina")
public class CocinaController {

    private final PedidoService pedidoService;

    @Autowired
    public CocinaController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("activePage","dashboard");
        model.addAttribute("pendientes", pedidoService.listarPorEstado(EstadoPedido.PENDIENTE).size());
        model.addAttribute("enPreparacion", pedidoService.listarPorEstado(EstadoPedido.EN_PREPARACION).size());
        return "cocina/dashboard";
    }

    @GetMapping("/pedidos")
    public String listarPedidos(Model model) {
        model.addAttribute("activePage","cocina");
        model.addAttribute("pendientes", pedidoService.listarPorEstado(EstadoPedido.PENDIENTE));
        model.addAttribute("enPreparacion", pedidoService.listarPorEstado(EstadoPedido.EN_PREPARACION));
        return "cocina/pedidos";
    }

    @PostMapping("/pedidos/{id}/iniciar")
    public String iniciarPreparacion(@PathVariable Long id) {
        pedidoService.iniciarPreparacion(id);
        return "redirect:/cocina/pedidos";
    }

    @PostMapping("/pedidos/{id}/listo")
    public String marcarListo(@PathVariable Long id) {
        pedidoService.marcarListo(id);
        return "redirect:/cocina/pedidos";
    }
}
