package com.perfumeria.pagosservice.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PagoDTO {

    @NotNull(message = "El ID de venta es obligatorio")
    private Long ventaId;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago; // EFECTIVO, TARJETA, TRANSFERENCIA

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private Double monto;
}
