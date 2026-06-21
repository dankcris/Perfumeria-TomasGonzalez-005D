package com.perfumeria.promocionesservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfumeria.promocionesservice.dto.PromocionDTO;
import com.perfumeria.promocionesservice.model.Promocion;
import com.perfumeria.promocionesservice.service.PromocionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/promociones")
@Tag(name = "Promociones", description = "CRUD de promociones y descuentos de la perfumería")
public class PromocionController {

    @Autowired
    private PromocionService service;

    @Operation(summary = "Listar todas las promociones")
    @GetMapping
    public ResponseEntity<List<Promocion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Listar solo las promociones activas")
    @GetMapping("/activas")
    public ResponseEntity<List<Promocion>> listarActivas() {
        return ResponseEntity.ok(service.listarActivas());
    }

    @Operation(summary = "Obtener una promoción por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Crear una nueva promoción")
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody PromocionDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar una promoción existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody PromocionDTO dto) {
        try {
            return ResponseEntity.ok(service.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar una promoción por ID")
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
