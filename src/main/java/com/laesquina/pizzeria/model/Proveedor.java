package com.laesquina.pizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/** Tabla 3 - Clase de Entidad: Proveedor. */
@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombreProveedor;

    @Column(length = 100)
    private String contacto;

    @Column(length = 200)
    private String productosSuministrados;

    // Lado inverso de Insumo.proveedor (ManyToOne). mappedBy evita crear
    // una segunda tabla de union; la FK vive en la tabla "insumo".
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.PERSIST)
    private List<Insumo> insumos = new ArrayList<>();

    public Proveedor() {
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getProductosSuministrados() {
        return productosSuministrados;
    }

    public void setProductosSuministrados(String productosSuministrados) {
        this.productosSuministrados = productosSuministrados;
    }

    public List<Insumo> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<Insumo> insumos) {
        this.insumos = insumos;
    }
}
