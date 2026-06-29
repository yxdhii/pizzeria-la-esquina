package com.laesquina.pizzeria.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Spring Security ya protege las rutas (ver SecurityConfig); este
 * controlador solo se encarga de la vista de login y de mandar a cada
 * empleado a SU dashboard despues de autenticarse (defaultSuccessUrl
 * apunta a "/redirigir").
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
