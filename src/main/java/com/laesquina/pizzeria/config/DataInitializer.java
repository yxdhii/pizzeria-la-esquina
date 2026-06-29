package com.laesquina.pizzeria.config;

import com.laesquina.pizzeria.model.*;
import com.laesquina.pizzeria.model.enums.*;
import com.laesquina.pizzeria.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Componente encargado de inicializar datos base del sistema cuando las tablas
 * principales se encuentran vacías. Esto permite contar con usuarios, mesas,
 * clientes, productos, proveedores e insumos iniciales para la ejecución y
 * validación funcional de la aplicación.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;
    private final ClienteRepository clienteRepository;
    private final MesaRepository mesaRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final InsumoRepository insumoRepository;
    private final InventarioRepository inventarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(EmpleadoRepository empleadoRepository,
                            ClienteRepository clienteRepository,
                            MesaRepository mesaRepository,
                            ProductoRepository productoRepository,
                            ProveedorRepository proveedorRepository,
                            InsumoRepository insumoRepository,
                            InventarioRepository inventarioRepository,
                            PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.clienteRepository = clienteRepository;
        this.mesaRepository = mesaRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.insumoRepository = insumoRepository;
        this.inventarioRepository = inventarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        sembrarEmpleados();
        sembrarMesas();
        sembrarClientes();
        Proveedor proveedor = sembrarProveedor();
        sembrarProductos();
        sembrarInsumos(proveedor);
    }

    private void sembrarEmpleados() {
        if (empleadoRepository.count() > 0) return;

        crearEmpleado("Carlos", "Saavedra", RolEmpleado.ADMINISTRADOR, "Diurno", "admin", "admin123");
        crearEmpleado("Lucia", "Ramos", RolEmpleado.CAJERO, "Tarde", "cajero1", "cajero123");
        crearEmpleado("Jose", "Quispe", RolEmpleado.MOZO, "Tarde", "mozo1", "mozo123");
        crearEmpleado("Maria", "Torres", RolEmpleado.COCINERO, "Tarde", "cocinero1", "cocina123");
        crearEmpleado("Pedro", "Lopez", RolEmpleado.LOGISTICA, "Mañana", "logistica1", "logistica123");
    }

    private void crearEmpleado(String nombre, String apellido, RolEmpleado rol, String turno, String usuario, String clave) {
        Empleado e = new Empleado();
        e.setNombre(nombre);
        e.setApellido(apellido);
        e.setRol(rol);
        e.setTurno(turno);
        e.setUsuario(usuario);
        e.setContrasena(passwordEncoder.encode(clave));
        empleadoRepository.save(e);
    }

    private void sembrarMesas() {
        if (mesaRepository.count() > 0) return;
        // Capacidades iniciales de las mesas registradas para la atención en salón.
        int[] capacidades = {2, 2, 4, 4, 4, 6, 6, 8};
        for (int i = 0; i < capacidades.length; i++) {
            Mesa m = new Mesa();
            m.setNumero(i + 1);
            m.setCapacidad(capacidades[i]);
            m.setEstado(EstadoMesa.DISPONIBLE);
            mesaRepository.save(m);
        }
    }

    private void sembrarClientes() {
        if (clienteRepository.count() > 0) return;

        Cliente c1 = new Cliente();
        c1.setNombre("Ana");
        c1.setApellido("Fernandez");
        c1.setTelefono("987654321");
        c1.setCorreo("ana.fernandez@mail.com");
        c1.setDireccion("Av. Las Flores 123, SJL");
        c1.setTipoCliente(TipoCliente.SALON);
        clienteRepository.save(c1);

        Cliente c2 = new Cliente();
        c2.setNombre("Luis");
        c2.setApellido("Gomez");
        c2.setTelefono("912345678");
        c2.setCorreo("luis.gomez@mail.com");
        c2.setDireccion("Jr. Los Pinos 456, SJL");
        c2.setTipoCliente(TipoCliente.LLEVAR);
        clienteRepository.save(c2);
    }

    private Proveedor sembrarProveedor() {
        if (proveedorRepository.count() > 0) {
            return proveedorRepository.findAll().get(0);
        }
        Proveedor p = new Proveedor();
        p.setNombreProveedor("Distribuidora Lacteos SJL");
        p.setContacto("987000111");
        p.setProductosSuministrados("Queso, jamon, masa, salsa de tomate");
        return proveedorRepository.save(p);
    }

    private void sembrarProductos() {
        if (productoRepository.count() > 0) return;

        crearProducto("Pizza Margarita", TipoProducto.PIZZA, 28.0);
        crearProducto("Pizza Pepperoni", TipoProducto.PIZZA, 32.0);
        crearProducto("Pizza Hawaiana", TipoProducto.PIZZA, 33.0);
        crearProducto("Gaseosa 500ml", TipoProducto.BEBIDA, 6.0);
        crearProducto("Agua mineral 500ml", TipoProducto.BEBIDA, 4.0);
        crearProducto("Pan al ajo", TipoProducto.COMPLEMENTO, 10.0);
    }

    private void crearProducto(String nombre, TipoProducto tipo, double precio) {
        Producto p = new Producto();
        p.setNombreProducto(nombre);
        p.setTipoProducto(tipo);
        p.setPrecio(precio);
        p.setDisponibilidad(true);
        productoRepository.save(p);
    }

    private void sembrarInsumos(Proveedor proveedor) {
        if (insumoRepository.count() > 0) return;

        crearInsumoConInventario("Queso mozzarella", "kg", 18.0, proveedor, 20.0, 5.0);
        crearInsumoConInventario("Jamon", "kg", 22.0, proveedor, 12.0, 4.0);
        crearInsumoConInventario("Masa de pizza", "unidad", 2.5, proveedor, 50.0, 10.0);
        crearInsumoConInventario("Salsa de tomate", "litro", 9.0, proveedor, 10.0, 3.0);
    }

    private void crearInsumoConInventario(String nombre, String unidad, double precioCompra,
                                           Proveedor proveedor, double stockActual, double stockMinimo) {
        Insumo insumo = new Insumo();
        insumo.setNombreInsumo(nombre);
        insumo.setUnidadMedida(unidad);
        insumo.setPrecioCompra(precioCompra);
        insumo.setProveedor(proveedor);
        Insumo guardado = insumoRepository.save(insumo);

        Inventario inv = new Inventario();
        inv.setInsumo(guardado);
        inv.setStockActual(stockActual);
        inv.setStockMinimo(stockMinimo);
        inventarioRepository.save(inv);
    }
}
