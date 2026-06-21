package com.perfumeria.ventasservice.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import com.perfumeria.ventasservice.dto.VentaDTO; import com.perfumeria.ventasservice.model.Venta; import com.perfumeria.ventasservice.service.VentaService;
import io.swagger.v3.oas.annotations.Operation; import io.swagger.v3.oas.annotations.tags.Tag;
@RestController @RequestMapping("/api/ventas")
@Tag(name="Ventas", description="CRUD de ventas de la perfumería")
public class VentaController {
    @Autowired private VentaService service;
    @Operation(summary="Listar todas las ventas") @GetMapping
    public ResponseEntity<List<Venta>> listar(){ return ResponseEntity.ok(service.listar()); }
    @Operation(summary="Obtener venta por ID") @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        try{ return ResponseEntity.ok(service.obtenerPorId(id)); } catch(RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary="Registrar una nueva venta") @PostMapping
    public ResponseEntity<Venta> guardar(@RequestBody VentaDTO dto){ return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto)); }
    @Operation(summary="Actualizar una venta existente") @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody VentaDTO dto){
        try{ return ResponseEntity.ok(service.actualizar(id,dto)); } catch(RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
    @Operation(summary="Eliminar una venta por ID") @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{ service.eliminar(id); return ResponseEntity.noContent().build(); } catch(RuntimeException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
    }
}
