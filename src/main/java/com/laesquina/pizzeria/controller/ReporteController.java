package com.laesquina.pizzeria.controller;

import com.laesquina.pizzeria.dto.ReporteVentasDTO;
import com.laesquina.pizzeria.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/** RF-005: "Generar reportes automaticos de ventas e inventarios". */
@Controller
@RequestMapping("/admin/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    @Autowired
    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/ventas")
    public String reporteVentas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model) {

        // Por defecto se muestra el mes en curso, para que el reporte no
        // aparezca vacio la primera vez que se abre la pantalla.
        LocalDate hoy = LocalDate.now();
        LocalDate desdeFinal = (desde != null) ? desde : hoy.withDayOfMonth(1);
        LocalDate hastaFinal = (hasta != null) ? hasta : hoy;

        List<ReporteVentasDTO> ventas = reporteService.reporteVentas(desdeFinal, hastaFinal);
        model.addAttribute("activePage", "reportes");
        model.addAttribute("ventas", ventas);
        model.addAttribute("desde", desdeFinal);
        model.addAttribute("hasta", hastaFinal);

        double total = ventas.stream()
                .mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0.0)
                .sum();
        model.addAttribute("totalVentas", total);
        return "admin/reportes/ventas";
    }

    @GetMapping("/inventario")
    public String reporteInventario(Model model) {
        model.addAttribute("activePage", "reportes");
        model.addAttribute("inventarios", reporteService.reporteInventarioBajoMinimo());
        return "admin/reportes/inventario";
    }
}
