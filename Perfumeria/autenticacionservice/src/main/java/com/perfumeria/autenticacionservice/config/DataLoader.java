package com.perfumeria.autenticacionservice.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.perfumeria.autenticacionservice.model.Rol;
import com.perfumeria.autenticacionservice.repository.RolRepository;

/**
 * Crea los roles base (ADMIN, VENDEDOR, CLIENTE) la primera vez que se levanta
 * el servicio, para no tener que insertarlos manualmente antes de registrar usuarios.
 */
@Component
public class DataLoader implements ApplicationRunner {

    private final RolRepository rolRepository;

    public DataLoader(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        crearRolSiNoExiste("ADMIN");
        crearRolSiNoExiste("VENDEDOR");
        crearRolSiNoExiste("CLIENTE");
    }

    private void crearRolSiNoExiste(String nombreRol) {

        if (rolRepository.findByNombreRol(nombreRol).isEmpty()) {

            Rol rol = new Rol();
            rol.setNombreRol(nombreRol);
            rolRepository.save(rol);
        }
    }
}
