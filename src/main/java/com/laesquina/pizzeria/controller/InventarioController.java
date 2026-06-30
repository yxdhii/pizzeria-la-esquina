package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador responsable de la administración del inventario del sistema.
 *
 * Permite consultar el estado del inventario, registrar movimientos
 * de entrada y salida de insumos, y actualizar los niveles mínimos
 * de stock para mantener un adecuado control de existencias.
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
    public String registrarEntrada(@PathVariable Long idInsumo,
            @RequestParam Double cantidad,
            RedirectAttributes redirectAttributes) {

        inventarioService.registrarEntrada(idInsumo, cantidad);

        redirectAttributes.addFlashAttribute(
                "success",
                "Entrada registrada correctamente.");

        return "redirect:/admin/inventario";
    }

    @PostMapping("/{idInsumo}/salida")
    public String registrarSalida(@PathVariable Long idInsumo,
            @RequestParam Double cantidad,
            RedirectAttributes redirectAttributes) {

        inventarioService.registrarSalida(idInsumo, cantidad);

        redirectAttributes.addFlashAttribute(
                "success",
                "Salida registrada correctamente.");

        return "redirect:/admin/inventario";
    }

    @PostMapping("/{id}/minimo")
    public String actualizarMinimo(@PathVariable Long id, @RequestParam Double stockMinimo) {
        inventarioService.actualizarStockMinimo(id, stockMinimo);
        return "redirect:/admin/inventario";
    }
}
