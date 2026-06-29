package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.model.Empleado;
import com.laesquina.pizzeria.model.enums.RolEmpleado;
import com.laesquina.pizzeria.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Controlador encargado de administrar la información de los empleados. */

@Controller
@RequestMapping("/admin/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService, PasswordEncoder passwordEncoder) {
        this.empleadoService = empleadoService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "empleados");
        model.addAttribute("empleados", empleadoService.listarTodos());
        return "admin/empleados/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("activePage", "empleados");
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("roles", RolEmpleado.values());
        return "admin/empleados/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("activePage", "empleados");
        model.addAttribute("empleado", empleadoService.buscarPorId(id));
        model.addAttribute("roles", RolEmpleado.values());
        return "admin/empleados/formulario";
    }

    // Conserva la contraseña actual cuando el campo se envía vacío;
    // en caso contrario, almacena la nueva contraseña cifrada.
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("empleado") Empleado empleado,
    BindingResult resultado,
    Model model,
    RedirectAttributes redirectAttributes) {
        boolean esNuevo = (empleado.getIdEmpleado() == null);
        if (resultado.hasErrors()) {
            model.addAttribute("roles", RolEmpleado.values());
            return "admin/empleados/formulario";
        }
        if (!esNuevo) {
            Empleado existente = empleadoService.buscarPorId(empleado.getIdEmpleado());
            if (empleado.getContrasena() == null || empleado.getContrasena().isBlank()) {
                empleado.setContrasena(existente.getContrasena());
            } else {
                empleado.setContrasena(passwordEncoder.encode(empleado.getContrasena()));
            }
        } else {
            empleado.setContrasena(passwordEncoder.encode(empleado.getContrasena()));
        }
        empleadoService.guardar(empleado);
        if (esNuevo) {
            redirectAttributes.addFlashAttribute("success", "Empleado registrado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("success", "Empleado actualizado correctamente.");
        }
        return "redirect:/admin/empleados";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        empleadoService.eliminar(id);
        redirectAttributes.addFlashAttribute(
            "success",
            "Empleado eliminado correctamente."
        );
        return "redirect:/admin/empleados";
    }
}
