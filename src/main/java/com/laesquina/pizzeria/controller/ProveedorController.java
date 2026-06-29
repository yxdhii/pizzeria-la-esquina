package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.model.Proveedor;
import com.laesquina.pizzeria.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "proveedores");
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "admin/proveedores/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("activePage", "proveedores");
        model.addAttribute("proveedor", new Proveedor());
        return "admin/proveedores/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("activePage", "proveedores");
        model.addAttribute("proveedor", proveedorService.buscarPorId(id));
        return "admin/proveedores/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("proveedor") Proveedor proveedor,
            BindingResult resultado,
            RedirectAttributes redirectAttributes) {

        boolean esNuevo = (proveedor.getIdProveedor() == null);

        if (resultado.hasErrors()) {
            return "admin/proveedores/formulario";
        }

        proveedorService.guardar(proveedor);

        if (esNuevo) {
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Proveedor registrado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Proveedor actualizado correctamente.");
        }

        return "redirect:/admin/proveedores";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        proveedorService.eliminar(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Proveedor eliminado correctamente.");

        return "redirect:/admin/proveedores";
    }
}
