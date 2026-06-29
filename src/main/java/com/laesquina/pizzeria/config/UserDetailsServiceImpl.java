package com.laesquina.pizzeria.config;

import com.laesquina.pizzeria.model.Empleado;
import com.laesquina.pizzeria.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Conecta Spring Security con la tabla "empleado". Cada Empleado.rol
 * (RolEmpleado) se traduce a una autoridad "ROLE_<rol>" (ej: ROLE_CAJERO),
 * que es lo que SecurityConfig usa en hasRole(...) para proteger las rutas
 * /admin/**, /cajero/**, /mozo/**, /cocina/**, /logistica/**.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public UserDetailsServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + usuario));

        return new User(
                empleado.getUsuario(),
                empleado.getContrasena(),
                List.of(new SimpleGrantedAuthority("ROLE_" + empleado.getRol().name()))
        );
    }
}
