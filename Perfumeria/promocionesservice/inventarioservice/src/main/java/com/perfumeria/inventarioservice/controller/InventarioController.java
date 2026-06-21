package com.perfumeria.inventarioservice.controller;
import com.perfumeria.inventarioservice.model.Inventario;
import com.perfumeria.inventarioservice.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "CRUD del inventario de la perfumería")
public class InventarioController {
    @Autowired
    private InventarioService service;
    @Operation(summary = "Listar todo el inventario")
    @GetMapping
    public ResponseEntity<List<Inventario>> listar(){ return ResponseEntity.ok(service.listar()); }
    @Operation(summary = "Obtener ítem de inventario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id){
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary = "Agregar ítem al inventario")
    @PostMapping
    public ResponseEntity<Inventario> guardar(@RequestBody Inventario inventario){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(inventario));
    }
    @Operation(summary = "Actualizar ítem del inventario")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Inventario inventario){
        try { return ResponseEntity.ok(service.actualizar(id, inventario)); }
        catch (RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary = "Eliminar ítem del inventario")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
}
