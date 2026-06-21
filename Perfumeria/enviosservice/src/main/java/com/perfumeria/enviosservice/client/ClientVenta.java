package com.perfumeria.enviosservice.client;

import com.perfumeria.enviosservice.dto.VentaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ventasservice", url = "http://localhost:8085")
public interface ClientVenta {

    @GetMapping("/api/ventas/{id}")
    VentaResponse obtenerVenta(@PathVariable Long id);
}
