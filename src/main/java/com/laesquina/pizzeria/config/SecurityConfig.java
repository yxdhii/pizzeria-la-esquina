package com.laesquina.pizzeria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Define quien puede entrar a que rutas. Se basa directamente en los actores
 * del caso de uso (Ilustracion 7): Mozo, Cajero, Cocinero, Encargado de
 * Logistica y Administrador. El Administrador tiene acceso a todo /admin/**
 * (gestion de catalogos: clientes, empleados, mesas, productos, proveedores,
 * insumos, reportes), mientras cada rol operativo solo entra a su propia
 * area, igual que en la pizzeria real un mozo no factura ni un cajero
 * administra el menu.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/cajero/**").hasRole("CAJERO")
                .requestMatchers("/mozo/**").hasRole("MOZO")
                .requestMatchers("/cocina/**").hasRole("COCINERO")
                .requestMatchers("/logistica/**").hasRole("LOGISTICA")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/redirigir", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .exceptionHandling(ex -> ex.accessDeniedPage("/error-403"));

        return http.build();
    }
}
