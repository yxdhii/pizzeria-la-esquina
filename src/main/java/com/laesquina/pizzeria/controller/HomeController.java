package com.laesquina.pizzeria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** 
 * Controlador encargado de gestionar el acceso a la ruta principal
 * del sistema, redirigiendo al usuario al módulo correspondiente
 * según el rol asignado.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/redirigir";
    }
}
