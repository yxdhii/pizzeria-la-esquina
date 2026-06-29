package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * "Realizacion del negocio": Actualizar inventario, Controlar stock (RF-009,
 * RF-0010), a cargo del Encargado de Logistica.
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
