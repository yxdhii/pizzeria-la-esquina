package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/** Panel del Administrador: accesos a todos los catalogos y reportes (RF-005). */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ClienteService clienteService;
    private final EmpleadoService empleadoService;
    private final ProductoService productoService;
    private final PedidoService pedidoService;
    private final InventarioService inventarioService;

    @Autowired
    public AdminController(ClienteService clienteService, EmpleadoService empleadoService,
                            ProductoService productoService, PedidoService pedidoService,
                            InventarioService inventarioService) {
        this.clienteService = clienteService;
        this.empleadoService = empleadoService;
        this.productoService = productoService;
        this.pedidoService = pedidoService;
        this.inventarioService = inventarioService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("totalClientes", clienteService.listarTodos().size());
        model.addAttribute("totalEmpleados", empleadoService.listarTodos().size());
        model.addAttribute("totalProductos", productoService.listarTodos().size());
        model.addAttribute("totalPedidos", pedidoService.listarTodos().size());
        model.addAttribute("alertasStock", inventarioService.listarStockBajoMinimo().size());
        return "admin/dashboard";
    }
}
