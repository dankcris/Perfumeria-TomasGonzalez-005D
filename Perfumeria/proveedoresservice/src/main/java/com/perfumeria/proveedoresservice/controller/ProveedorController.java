package com.perfumeria.proveedoresservice.controller;
import com.perfumeria.proveedoresservice.model.Proveedor; import com.perfumeria.proveedoresservice.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation; import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/proveedores")
@Tag(name="Proveedores", description="CRUD de proveedores de la perfumería")
public class ProveedorController {
    @Autowired private ProveedorService service;
    @Operation(summary="Listar todos los proveedores") @GetMapping
    public ResponseEntity<List<Proveedor>> listar(){ return ResponseEntity.ok(service.listar()); }
    @Operation(summary="Obtener proveedor por ID") @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id){
        try{ return ResponseEntity.ok(service.buscarPorId(id)); } catch(RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary="Crear un nuevo proveedor") @PostMapping
    public ResponseEntity<Proveedor> guardar(@RequestBody Proveedor proveedor){ return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(proveedor)); }
    @Operation(summary="Actualizar un proveedor existente") @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Proveedor proveedor){
        try{ return ResponseEntity.ok(service.actualizar(id, proveedor)); } catch(RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary="Eliminar un proveedor por ID") @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{ service.eliminar(id); return ResponseEntity.noContent().build(); } catch(RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
}
