package com.laesquina.pizzeria.config;

import com.laesquina.pizzeria.model.Proveedor;
import com.laesquina.pizzeria.repository.ProveedorRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Conversor personalizado que transforma el identificador de un proveedor
 * recibido desde un formulario en una instancia de la entidad Proveedor.
 * 
 * Este componente facilita el enlace de datos (Data Binding) durante el
 * procesamiento de formularios en Spring MVC.
 */
@Component
public class StringToProveedorConverter implements Converter<String, Proveedor> {

    private final ProveedorRepository proveedorRepository;

    public StringToProveedorConverter(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public Proveedor convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return proveedorRepository.findById(Long.parseLong(source)).orElse(null);
    }
}
