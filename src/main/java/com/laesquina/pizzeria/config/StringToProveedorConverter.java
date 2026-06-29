package com.laesquina.pizzeria.config;

import com.laesquina.pizzeria.model.Proveedor;
import com.laesquina.pizzeria.repository.ProveedorRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Sin este Converter, el formulario de Insumo (que tiene un <select> que
 * envia solo el idProveedor como texto) fallaria al hacer binding contra
 * Insumo.proveedor, que es de tipo Proveedor y no String/Long. Spring MVC
 * usa automaticamente cualquier Converter<String, X> registrado como Bean
 * para resolver este caso.
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
