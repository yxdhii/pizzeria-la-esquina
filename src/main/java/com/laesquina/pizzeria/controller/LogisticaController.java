package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsable de las funcionalidades del módulo de logística.
 *
 * Permite supervisar el estado del inventario y administrar los
 * movimientos de entrada y salida de insumos para mantener el
 * abastecimiento de la pizzería.
 */
@Controller
@RequestMapping("/logistica")
public class LogisticaController {

    private final InventarioService inventarioService;

    @Autowired
    public LogisticaController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("alertasStock", inventarioService.listarStockBajoMinimo().size());
        return "logistica/dashboard";
    }

    @GetMapping("/inventario")
    public String listar(Model model) {
        model.addAttribute("activePage", "inventario");
        model.addAttribute("inventarios", inventarioService.listarTodos());
        return "logistica/inventario";
    }

    @PostMapping("/inventario/{idInsumo}/entrada")
    public String registrarEntrada(@PathVariable Long idInsumo, @RequestParam Double cantidad) {
        inventarioService.registrarEntrada(idInsumo, cantidad);
        return "redirect:/logistica/inventario";
    }

    @PostMapping("/inventario/{idInsumo}/salida")
    public String registrarSalida(@PathVariable Long idInsumo, @RequestParam Double cantidad) {
        inventarioService.registrarSalida(idInsumo, cantidad);
        return "redirect:/logistica/inventario";
    }
}
