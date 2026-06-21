package com.perfumeria.clientesservice.controller;
import com.perfumeria.clientesservice.dto.ClienteDTO;
import com.perfumeria.clientesservice.model.Cliente;
import com.perfumeria.clientesservice.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "CRUD de clientes de la perfumería")
public class ClienteController {
    @Autowired
    private ClienteService service;
    @Operation(summary = "Listar todos los clientes")
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() { return ResponseEntity.ok(service.listar()); }
    @Operation(summary = "Obtener cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try { return ResponseEntity.ok(service.buscarPorId(id)); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary = "Crear un nuevo cliente")
    @PostMapping
    public ResponseEntity<Cliente> guardar(@RequestBody ClienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }
    @Operation(summary = "Actualizar un cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        try { return ResponseEntity.ok(service.actualizar(id, dto)); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary = "Eliminar un cliente por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try { service.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
}
