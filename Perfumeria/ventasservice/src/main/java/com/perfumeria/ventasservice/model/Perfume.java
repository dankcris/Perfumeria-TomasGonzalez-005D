package com.perfumeria.ventasservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Perfume {

    private Long id;
    private String marca;
    private String nombre;
    private double precio;
    private int stock;
}