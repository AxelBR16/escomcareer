package com.example.demo.security.services;

import com.example.demo.security.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UserService userService;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar en la tabla de administradores
        Optional<Administrador> adminOpt = userService.getAdministradorByEmail(email);
        if (adminOpt.isPresent()) {
            return AdminMain.build(adminOpt.get());
        }

        // Buscar en la tabla de aspirantes
        Optional<Aspirante> aspiranteOpt = userService.getAspiranteByEmail(email);
        if (aspiranteOpt.isPresent()) {
            return AspiranteMain.build(aspiranteOpt.get());
        }

        // Buscar en la tabla de egresados
        Optional<Egresado> egresadoOpt = userService.getEgresadoByEmail(email);
        if (egresadoOpt.isPresent()) {
            return EgresadoMain.build(egresadoOpt.get());
        }

        throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + email);
    }

}
