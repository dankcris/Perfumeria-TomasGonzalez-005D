package com.perfumeria.ventasservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {

    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
}