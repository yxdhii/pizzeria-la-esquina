package com.laesquina.pizzeria.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador responsable del inicio de sesión y la gestión del acceso
 * al sistema. Además, redirige a los usuarios autenticados al módulo
 * correspondiente según el rol asignado y muestra la página de acceso
 * denegado cuando el usuario no cuenta con los permisos necesarios.
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/redirigir")
    public String redirigirSegunRol(Authentication authentication) {
        for (GrantedAuthority autoridad : authentication.getAuthorities()) {
            switch (autoridad.getAuthority()) {
                case "ROLE_ADMINISTRADOR": return "redirect:/admin/dashboard";
                case "ROLE_CAJERO": return "redirect:/cajero/dashboard";
                case "ROLE_MOZO": return "redirect:/mozo/dashboard";
                case "ROLE_COCINERO": return "redirect:/cocina/dashboard";
                case "ROLE_LOGISTICA": return "redirect:/logistica/dashboard";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/error-403")
    public String accesoDenegado(Model model) {
        model.addAttribute("mensaje", "No tiene permisos para acceder a esta sección.");
        return "error";
    }
}
