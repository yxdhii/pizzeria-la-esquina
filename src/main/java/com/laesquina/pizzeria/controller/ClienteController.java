package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.model.Cliente;
import com.laesquina.pizzeria.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Controlador encargado de administrar la información de los clientes. */

@Controller
@RequestMapping("/admin/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "clientes");
        model.addAttribute("clientes", clienteService.listarTodos());
        return "admin/clientes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("activePage", "clientes");
        model.addAttribute("cliente", new Cliente());
        return "admin/clientes/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("activePage", "clientes");
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        return "admin/clientes/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente,
    BindingResult resultado,
    RedirectAttributes redirectAttributes) {
        boolean esNuevo = (cliente.getIdCliente() == null);
        if (resultado.hasErrors()) {
            return "admin/clientes/formulario";
        }
        
        clienteService.guardar(cliente);
        if (esNuevo) {
            redirectAttributes.addFlashAttribute(
                "success",
                "Cliente registrado correctamente."
            );
        } else {
            redirectAttributes.addFlashAttribute(
                "success",
                "Cliente actualizado correctamente."
            );
        }
        return "redirect:/admin/clientes";
    }

   @GetMapping("/{id}/eliminar")
   public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
           clienteService.eliminar(id);
            redirectAttributes.addFlashAttribute(
                "success",
                 "Cliente eliminado correctamente."
    );
        return "redirect:/admin/clientes";
    }
}
