package com.laesquina.pizzeria.model;

import com.laesquina.pizzeria.model.enums.RolEmpleado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad que representa a los empleados registrados en el sistema,
 * incluyendo la información necesaria para su identificación,
 * autenticación y asignación de funciones.
 */
@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(length = 80)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RolEmpleado rol;

    @Column(length = 30)
    private String turno;

    @NotBlank(message = "El usuario es obligatorio")
    @Column(nullable = false, unique = true, length = 40)
    private String usuario;

    
    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false, length = 100)
    private String contrasena;

    public Empleado() {
    }

    // ---- Getters y Setters ----
    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public RolEmpleado getRol() {
        return rol;
    }

    public void setRol(RolEmpleado rol) {
        this.rol = rol;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreCompleto() {
        return nombre + (apellido != null ? " " + apellido : "");
    }
}
