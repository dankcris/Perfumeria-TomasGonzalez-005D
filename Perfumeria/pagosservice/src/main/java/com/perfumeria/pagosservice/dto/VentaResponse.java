package com.perfumeria.pagosservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VentaResponse {
    private Long id;
    private Long clienteId;
    private Long perfumeId;
    private int cantidad;
    private double total;
}
