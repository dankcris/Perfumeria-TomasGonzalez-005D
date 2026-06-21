package com.perfumeria.perfumesservice.controller;
import com.perfumeria.perfumesservice.model.Perfume;
import com.perfumeria.perfumesservice.service.PerfumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/perfumes")
@RequiredArgsConstructor
@Tag(name = "Perfumes", description = "CRUD de perfumes de la tienda")
public class PerfumeController {
    private final PerfumeService service;
    @Operation(summary = "Listar todos los perfumes")
    @GetMapping
    public ResponseEntity<List<Perfume>> listar(){ return ResponseEntity.ok(service.listar()); }
    @Operation(summary = "Obtener perfume por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id){
        try { return ResponseEntity.ok(service.buscar(id)); }
        catch(RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary = "Crear un nuevo perfume")
    @PostMapping
    public ResponseEntity<Perfume> guardar(@RequestBody Perfume perfume){ return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(perfume)); }
    @Operation(summary = "Actualizar un perfume existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Perfume perfume){
        try { return ResponseEntity.ok(service.actualizar(id, perfume)); }
        catch(RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary = "Eliminar un perfume por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch(RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
}
