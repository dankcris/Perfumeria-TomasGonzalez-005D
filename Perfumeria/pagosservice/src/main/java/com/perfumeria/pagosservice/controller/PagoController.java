package com.perfumeria.pagosservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfumeria.pagosservice.dto.PagoDTO;
import com.perfumeria.pagosservice.model.Pago;
import com.perfumeria.pagosservice.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Gestión de pagos asociados a ventas")
public class PagoController {

    @Autowired
    private PagoService service;

    @Operation(summary = "Listar todos los pagos")
    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Listar pagos por ID de venta")
    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<Pago>> listarPorVenta(@PathVariable Long ventaId) {
        return ResponseEntity.ok(service.listarPorVenta(ventaId));
    }

    @Operation(summary = "Obtener un pago por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Registrar un nuevo pago para una venta")
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody PagoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar un pago existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        try {
            return ResponseEntity.ok(service.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un pago por ID")
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
