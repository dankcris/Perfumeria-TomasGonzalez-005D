package com.perfumeria.enviosservice.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EnvioDTO {

    @NotNull(message = "El ID de venta es obligatorio")
    private Long ventaId;

    @NotBlank(message = "La dirección de destino es obligatoria")
    private String direccionDestino;

    @NotBlank(message = "La ciudad de destino es obligatoria")
    private String ciudadDestino;

    @NotBlank(message = "El transportista es obligatorio")
    private String transportista; // CORREOS_CHILE, CHILEXPRESS, STARKEN
}
