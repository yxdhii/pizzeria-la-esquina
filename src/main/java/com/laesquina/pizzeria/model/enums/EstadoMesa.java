package com.laesquina.pizzeria.model.enums;

/**
 * La entidad Mesa no figura en la Tabla 3 del documento, pero es necesaria
 * porque la descripcion de la empresa indica capacidad para 8 mesas y el
 * RF-004 exige "Gestion de asignacion de mesas y ocupacion".
 */
public enum EstadoMesa {
    DISPONIBLE,
    OCUPADA,
    RESERVADA
}
