package com.laesquina.pizzeria.service.impl;

import com.laesquina.pizzeria.exception.RecursoNoEncontradoException;
import com.laesquina.pizzeria.exception.StockInsuficienteException;
import com.laesquina.pizzeria.model.Inventario;
import com.laesquina.pizzeria.repository.InventarioRepository;
import com.laesquina.pizzeria.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Realiza la "Realizacion del negocio": Actualizar inventario (entrada y
 * salida de insumos) y Controlar stock (RF-009).
 */
@Service
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;

    @Autowired
    public InventarioServiceImpl(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public List<Inventario> listarTodos() {
        return inventarioRepository.findAll();
    }

    @Override
    public List<Inventario> listarStockBajoMinimo() {
        return inventarioRepository.findStockBajoMinimo();
    }

    @Override
    public Inventario buscarPorId(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Registro de inventario no encontrado con id " + id));
    }

    @Override
    @Transactional
    public Inventario registrarEntrada(Long idInsumo, Double cantidad) {
        Inventario inv = inventarioRepository.findByInsumo_IdInsumo(idInsumo)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe inventario para el insumo " + idInsumo));
        inv.setStockActual(inv.getStockActual() + cantidad);
        inv.setFechaActualizacion(LocalDateTime.now());
        return inventarioRepository.save(inv);
    }

    @Override
    @Transactional
    public Inventario registrarSalida(Long idInsumo, Double cantidad) {
        Inventario inv = inventarioRepository.findByInsumo_IdInsumo(idInsumo)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe inventario para el insumo " + idInsumo));
        if (inv.getStockActual() < cantidad) {
            throw new StockInsuficienteException(
                    "Stock insuficiente de " + inv.getInsumo().getNombreInsumo()
                            + " (disponible: " + inv.getStockActual() + ")");
        }
        inv.setStockActual(inv.getStockActual() - cantidad);
        inv.setFechaActualizacion(LocalDateTime.now());
        return inventarioRepository.save(inv);
    }

    @Override
    @Transactional
    public void actualizarStockMinimo(Long idInventario, Double nuevoMinimo) {
        Inventario inv = buscarPorId(idInventario);
        inv.setStockMinimo(nuevoMinimo);
        inventarioRepository.save(inv);
    }
}
