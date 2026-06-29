package com.laesquina.pizzeria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Si alguien autenticado visita "/", lo mandamos a que se le redirija segun su rol. */
@Controller
public class HomeController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/redirigir";
    }
}
