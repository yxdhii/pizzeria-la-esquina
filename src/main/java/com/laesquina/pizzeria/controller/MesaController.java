package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.model.Mesa;
import com.laesquina.pizzeria.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/mesas")
public class MesaController {

    private final MesaService mesaService;

    @Autowired
    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "mesas");
        model.addAttribute("mesas", mesaService.listarTodas());
        return "admin/mesas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("activePage", "mesas");
        model.addAttribute("mesa", new Mesa());
        return "admin/mesas/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("activePage", "mesas");
        model.addAttribute("mesa", mesaService.buscarPorId(id));
        return "admin/mesas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("mesa") Mesa mesa,
            BindingResult resultado,
            RedirectAttributes redirectAttributes) {

        boolean esNuevo = (mesa.getIdMesa() == null);

        if (resultado.hasErrors()) {
            return "admin/mesas/formulario";
        }

        mesaService.guardar(mesa);

        if (esNuevo) {
            redirectAttributes.addFlashAttribute("success", "Mesa registrada correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("success", "Mesa actualizada correctamente.");
        }

        return "redirect:/admin/mesas";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        mesaService.eliminar(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Mesa eliminada correctamente.");

        return "redirect:/admin/mesas";
    }
}
