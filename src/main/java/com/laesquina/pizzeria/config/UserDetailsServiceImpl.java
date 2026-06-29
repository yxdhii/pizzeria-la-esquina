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
 * Implementación de la interfaz UserDetailsService utilizada por
 * Spring Security para autenticar usuarios.
 * 
 * Recupera la información del empleado desde la base de datos y
 * asigna las autoridades correspondientes según el rol registrado.
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
