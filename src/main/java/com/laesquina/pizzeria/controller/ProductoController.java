package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.model.Producto;
import com.laesquina.pizzeria.model.enums.TipoProducto;
import com.laesquina.pizzeria.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "productos");
        model.addAttribute("productos", productoService.listarTodos());
        return "admin/productos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("activePage", "productos");
        model.addAttribute("producto", new Producto());
        model.addAttribute("tipos", TipoProducto.values());
        return "admin/productos/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("activePage", "productos");
        model.addAttribute("producto", productoService.buscarPorId(id));
        model.addAttribute("tipos", TipoProducto.values());
        return "admin/productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        boolean esNuevo = (producto.getIdProducto() == null);

        if (resultado.hasErrors()) {
            model.addAttribute("tipos", TipoProducto.values());
            return "admin/productos/formulario";
        }

        productoService.guardar(producto);

        if (esNuevo) {
            redirectAttributes.addFlashAttribute("success", "Producto registrado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("success", "Producto actualizado correctamente.");
        }

        return "redirect:/admin/productos";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        productoService.eliminar(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Producto eliminado correctamente.");

        return "redirect:/admin/productos";
    }
}
