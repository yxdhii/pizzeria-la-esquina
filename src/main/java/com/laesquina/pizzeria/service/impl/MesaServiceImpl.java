package com.laesquina.pizzeria.service.impl;

import com.laesquina.pizzeria.exception.RecursoNoEncontradoException;
import com.laesquina.pizzeria.model.Mesa;
import com.laesquina.pizzeria.repository.MesaRepository;
import com.laesquina.pizzeria.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;

    @Autowired
    public MesaServiceImpl(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Override
    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    @Override
    public Mesa buscarPorId(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Mesa no encontrada con id " + id));
    }

    @Override
    public Mesa guardar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    @Override
    public void eliminar(Long id) {
        mesaRepository.deleteById(id);
    }
}
