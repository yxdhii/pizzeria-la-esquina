package com.laesquina.pizzeria.model;

import com.laesquina.pizzeria.model.enums.EstadoPedido;
import com.laesquina.pizzeria.model.enums.TipoPedido;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabla 3 - Clase de Entidad: Pedido.
 * - cliente: ManyToOne (RF-001).
 * - mesa: ManyToOne, opcional -> solo aplica si tipoPedido = SALON (RF-004).
 * - empleado: quien registro el pedido (Mozo/Cajero segun el diagrama BPMN).
 * - detalles: un pedido tiene N productos con su cantidad -> ver DetallePedido.
 * - factura: relacion 1 a 1, se crea al cobrar (caso de uso "Cobrar/Registrar Venta").
 */
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Column(nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoPedido tipoPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @Column(nullable = false)
    private Double total = 0.0;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_mesa")
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    // fetch = EAGER (no es el default de @OneToMany) porque con
    // spring.jpa.open-in-view=false la sesion de Hibernate se cierra al
    // terminar el controlador, antes de renderizar la vista. Si esta
    // coleccion fuera LAZY, las plantillas de Cocina/Mozo que muestran
    // pedido.detalles lanzarian LazyInitializationException al intentar
    // leerla durante el render. Como un Pedido siempre se necesita junto
    // con sus lineas (no tiene sentido mostrar uno sin el otro), EAGER es
    // la opcion correcta aqui y no solo un parche.
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetallePedido> detalles = new ArrayList<>();

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Factura factura;

    public Pedido() {
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    /** Mantiene ambos lados de la relacion sincronizados (buena practica con JPA). */
    public void agregarDetalle(DetallePedido detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }
}
