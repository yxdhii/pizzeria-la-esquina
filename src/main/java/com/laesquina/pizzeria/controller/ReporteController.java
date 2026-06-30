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

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

import java.time.format.DateTimeFormatter;

/**
 * Controlador responsable de la generación y consulta de los
 * reportes de ventas e inventario, proporcionando información
 * para el seguimiento de las operaciones de la pizzería.
 */
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

        // Se establece el mes actual como rango predeterminado cuando
        // no se proporcionan fechas de consulta.
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

    @GetMapping("/ventas/pdf")
    public void exportarVentasPdf(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            HttpServletResponse response) throws Exception {

        LocalDate hoy = LocalDate.now();
        LocalDate desdeFinal = (desde != null) ? desde : hoy.withDayOfMonth(1);
        LocalDate hastaFinal = (hasta != null) ? hasta : hoy;

        List<ReporteVentasDTO> ventas = reporteService.reporteVentas(desdeFinal, hastaFinal);

        double total = ventas.stream()
                .mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0.0)
                .sum();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_ventas.pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        Paragraph title = new Paragraph("Reporte de Ventas - Pizzería La Esquina", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15);
        document.add(title);

        Paragraph rango = new Paragraph("Desde: " + desdeFinal + "   Hasta: " + hastaFinal, bodyFont);
        rango.setAlignment(Element.ALIGN_CENTER);
        rango.setSpacingAfter(20);
        document.add(rango);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[] { 1.2f, 2.2f, 3f, 2f, 1.6f });

        String[] headers = { "Factura", "Fecha", "Cliente", "Método", "Total" };

        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (ReporteVentasDTO v : ventas) {
            table.addCell(new Phrase(String.valueOf(v.getIdFactura()), bodyFont));
            table.addCell(new Phrase(v.getFecha().format(formatter), bodyFont));
            table.addCell(new Phrase(v.getCliente(), bodyFont));
            table.addCell(new Phrase(v.getMetodoPago(), bodyFont));
            table.addCell(new Phrase("S/ " + String.format("%.2f", v.getTotal()), bodyFont));
        }

        document.add(table);

        Paragraph totalText = new Paragraph("\nTotal del periodo: S/ " + String.format("%.2f", total), headerFont);
        totalText.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalText);

        document.close();
    }
}
