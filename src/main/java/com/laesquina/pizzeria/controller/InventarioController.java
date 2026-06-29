package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * RF-009: alertas de stock minimo. Se expone bajo /admin/** (gestion
 * general) y se reutiliza tambien desde LogisticaController para el rol
 * Encargado de Logistica, que es quien opera el inventario dia a dia segun
 * el documento (seccion 2.1.2: "Personal de logistica... controla insumos").
 */
@Controller
@RequestMapping("/admin/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    @Autowired
    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "inventario");
        model.addAttribute("inventarios", inventarioService.listarTodos());
        return "admin/inventario/lista";
    }

    @PostMapping("/{idInsumo}/entrada")
    public String registrarEntrada(@PathVariable Long idInsumo, @RequestParam Double cantidad) {
        inventarioService.registrarEntrada(idInsumo, cantidad);
        return "redirect:/admin/inventario";
    }

    @PostMapping("/{idInsumo}/salida")
    public String registrarSalida(@PathVariable Long idInsumo, @RequestParam Double cantidad) {
        inventarioService.registrarSalida(idInsumo, cantidad);
        return "redirect:/admin/inventario";
    }

    @PostMapping("/{id}/minimo")
    public String actualizarMinimo(@PathVariable Long id, @RequestParam Double stockMinimo) {
        inventarioService.actualizarStockMinimo(id, stockMinimo);
        return "redirect:/admin/inventario";
    }
}
