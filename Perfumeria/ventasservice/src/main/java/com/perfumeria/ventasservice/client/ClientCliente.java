package com.perfumeria.ventasservice.client;

import com.perfumeria.ventasservice.model.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientesservice", url = "http://localhost:8081")
public interface ClientCliente {

    @GetMapping("/api/clientes/{id}")
    Cliente obtenerCliente(@PathVariable Long id);
}
