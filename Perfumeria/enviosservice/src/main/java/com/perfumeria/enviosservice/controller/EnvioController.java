package com.perfumeria.enviosservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfumeria.enviosservice.dto.EnvioDTO;
import com.perfumeria.enviosservice.model.Envio;
import com.perfumeria.enviosservice.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/envios")
@Tag(name = "Envíos", description = "Gestión de envíos asociados a ventas")
public class EnvioController {

    @Autowired
    private EnvioService service;

    @Operation(summary = "Listar todos los envíos")
    @GetMapping
    public ResponseEntity<List<Envio>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Listar envíos por ID de venta")
    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<Envio>> listarPorVenta(@PathVariable Long ventaId) {
        return ResponseEntity.ok(service.listarPorVenta(ventaId));
    }

    @Operation(summary = "Listar envíos por estado (PENDIENTE, EN_CAMINO, ENTREGADO)")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Envio>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }

    @Operation(summary = "Obtener un envío por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Crear un nuevo envío para una venta")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody EnvioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar el estado de un envío")
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            return ResponseEntity.ok(service.actualizarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un envío por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
