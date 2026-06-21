package com.perfumeria.autenticacionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String token;

    private String tipo = "Bearer";

    public AuthResponseDTO(String token) {
        this.token = token;
        this.tipo = "Bearer";
    }
}
