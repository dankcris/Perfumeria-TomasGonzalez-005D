package com.perfumeria.ventasservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaDTO {

    private Long clienteId;

    private Long perfumeId;

    private int cantidad;
}