package com.perfumeria.ventasservice.controller;

import com.perfumeria.ventasservice.dto.VentaDTO;
import com.perfumeria.ventasservice.model.Venta;
import com.perfumeria.ventasservice.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService service;

    @PostMapping
    public ResponseEntity<Venta> guardar(
            @RequestBody VentaDTO dto){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }
}