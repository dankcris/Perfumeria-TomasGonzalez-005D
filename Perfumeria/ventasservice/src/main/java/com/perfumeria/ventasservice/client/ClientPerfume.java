package com.perfumeria.ventasservice.client;

import com.perfumeria.ventasservice.model.Perfume;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "perfumesservice", url = "http://localhost:8083")
public interface ClientPerfume {

    @GetMapping("/api/perfumes/{id}")
    Perfume obtenerPerfume(@PathVariable Long id);
}
