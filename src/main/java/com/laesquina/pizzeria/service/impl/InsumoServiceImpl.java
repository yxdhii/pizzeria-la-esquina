package com.laesquina.pizzeria.service.impl;

import com.laesquina.pizzeria.exception.RecursoNoEncontradoException;
import com.laesquina.pizzeria.model.Insumo;
import com.laesquina.pizzeria.model.Inventario;
import com.laesquina.pizzeria.repository.InsumoRepository;
import com.laesquina.pizzeria.repository.InventarioRepository;
import com.laesquina.pizzeria.service.InsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository insumoRepository;
    private final InventarioRepository inventarioRepository;

    @Autowired
    public InsumoServiceImpl(InsumoRepository insumoRepository, InventarioRepository inventarioRepository) {
        this.insumoRepository = insumoRepository;
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public List<Insumo> listarTodos() {
        return insumoRepository.findAll();
    }

    @Override
    public Insumo buscarPorId(Long id) {
        return insumoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado con id " + id));
    }

    /**
     * @Transactional: si falla el guardado del Inventario, tampoco debe
     * quedar guardado el Insumo (ambos o ninguno), ya que la relacion 1 a 1
     * es obligatoria (Inventario.insumo es not null).
     */
    @Override
    @Transactional
    public Insumo guardar(Insumo insumo, Double stockInicial, Double stockMinimo) {
        boolean esNuevo = insumo.getIdInsumo() == null;
        insumo.setFechaActualizacion(LocalDateTime.now());
        Insumo guardado = insumoRepository.save(insumo);

        if (esNuevo) {
            Inventario inventario = new Inventario();
            inventario.setInsumo(guardado);
            inventario.setStockActual(stockInicial != null ? stockInicial : 0.0);
            inventario.setStockMinimo(stockMinimo != null ? stockMinimo : 0.0);
            inventarioRepository.save(inventario);
        }
        return guardado;
    }

    @Override
    public void eliminar(Long id) {
        insumoRepository.deleteById(id);
    }
}
