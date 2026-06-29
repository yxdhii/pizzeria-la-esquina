package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.model.Insumo;
import com.laesquina.pizzeria.service.InsumoService;
import com.laesquina.pizzeria.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** RF-0010: "Registrar proveedores y entradas de insumos". */
@Controller
@RequestMapping("/admin/insumos")
public class InsumoController {

    private final InsumoService insumoService;
    private final ProveedorService proveedorService;

    @Autowired
    public InsumoController(InsumoService insumoService, ProveedorService proveedorService) {
        this.insumoService = insumoService;
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "insumos");
        model.addAttribute("insumos", insumoService.listarTodos());
        return "admin/insumos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("activePage", "insumos");
        model.addAttribute("insumo", new Insumo());
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "admin/insumos/formulario";
    }

    // stockInicial/stockMinimo viajan como parametros sueltos (no son parte
    // de la entidad Insumo) porque al crear un insumo nuevo tambien se debe
    // crear su Inventario asociado; ver InsumoServiceImpl.guardar(...).
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("insumo") Insumo insumo,
            BindingResult resultado,
            @RequestParam(required = false) Double stockInicial,
            @RequestParam(required = false) Double stockMinimo,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            model.addAttribute("proveedores", proveedorService.listarTodos());
            return "admin/insumos/formulario";
        }

        insumoService.guardar(insumo, stockInicial, stockMinimo);

        redirectAttributes.addFlashAttribute(
                "success",
                "Insumo registrado correctamente.");

        return "redirect:/admin/insumos";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        insumoService.eliminar(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Insumo eliminado correctamente.");

        return "redirect:/admin/insumos";
    }
}
