package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.dto.PedidoRequestDTO;
import com.laesquina.pizzeria.model.Empleado;
import com.laesquina.pizzeria.model.enums.EstadoMesa;
import com.laesquina.pizzeria.model.enums.EstadoPedido;
import com.laesquina.pizzeria.repository.EmpleadoRepository;
import com.laesquina.pizzeria.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones del módulo de mozo.
 *
 * Permite registrar, consultar, entregar y cancelar pedidos, así como
 * visualizar la información necesaria para la atención de los clientes.
 */
@Controller
@RequestMapping("/mozo")
public class MozoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final MesaService mesaService;
    private final ProductoService productoService;
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public MozoController(PedidoService pedidoService, ClienteService clienteService, MesaService mesaService,
            ProductoService productoService, EmpleadoRepository empleadoRepository) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.mesaService = mesaService;
        this.productoService = productoService;
        this.empleadoRepository = empleadoRepository;
    }

    /**
    * Obtiene la información del empleado autenticado para asociar las
    * operaciones realizadas con el usuario que inició sesión.
    */
    private Empleado empleadoActual(Authentication authentication) {
        return empleadoRepository.findByUsuario(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("Empleado no encontrado para el usuario logueado"));
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("mesas", mesaService.listarTodas());
        model.addAttribute("pedidosPendientes", pedidoService.listarPorEstado(EstadoPedido.PENDIENTE).size());
        model.addAttribute("pedidosListos", pedidoService.listarPorEstado(EstadoPedido.LISTO).size());
        return "mozo/dashboard";
    }

    @GetMapping("/pedidos")
    public String listarPedidos(Model model) {
        model.addAttribute("activePage", "pedidos");
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "mozo/pedidos";
    }

    @GetMapping("/pedidos/nuevo")
    public String nuevoPedidoFormulario(Model model) {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        model.addAttribute("activePage", "nuevoPedido");
        model.addAttribute("pedidoRequest", dto);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("mesas", mesaService.listarTodas().stream()
                .filter(m -> m.getEstado() == EstadoMesa.DISPONIBLE).toList());
        model.addAttribute("productos", productoService.listarDisponibles());
        return "mozo/registrar-pedido";
    }

    @PostMapping("/pedidos/guardar")
    public String guardarPedido(
            @Valid @ModelAttribute("pedidoRequest") PedidoRequestDTO dto,
            BindingResult resultado,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            model.addAttribute("activePage", "nuevoPedido");
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("mesas", mesaService.listarTodas().stream()
                    .filter(m -> m.getEstado() == EstadoMesa.DISPONIBLE).toList());
            model.addAttribute("productos", productoService.listarDisponibles());
            model.addAttribute("error", "Revisa los datos del pedido.");
            return "mozo/registrar-pedido";
        }

        dto.setIdEmpleado(empleadoActual(authentication).getIdEmpleado());
        pedidoService.registrarPedido(dto);

        redirectAttributes.addFlashAttribute("success", "Pedido registrado correctamente.");
        return "redirect:/mozo/pedidos";
    }

    @PostMapping("/pedidos/{id}/entregar")
    public String entregarPedido(@PathVariable Long id) {
        pedidoService.marcarEntregado(id);
        return "redirect:/mozo/pedidos";
    }

    @PostMapping("/pedidos/{id}/cancelar")
    public String cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return "redirect:/mozo/pedidos";
    }
}
