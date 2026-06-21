package com.perfumeria.pagosservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfumeria.pagosservice.client.ClientVenta;
import com.perfumeria.pagosservice.dto.PagoDTO;
import com.perfumeria.pagosservice.dto.VentaResponse;
import com.perfumeria.pagosservice.model.Pago;
import com.perfumeria.pagosservice.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private ClientVenta clientVenta;

    public List<Pago> listar() {
        return repository.findAll();
    }

    public List<Pago> listarPorVenta(Long ventaId) {
        return repository.findByVentaId(ventaId);
    }

    public Pago obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
    }

    public Pago registrar(PagoDTO dto) {
        // Validar que la venta exista consultando ventasservice vía Feign
        VentaResponse venta = clientVenta.obtenerVenta(dto.getVentaId());

        if (venta == null) {
            throw new RuntimeException("La venta con id " + dto.getVentaId() + " no existe.");
        }

        Pago pago = new Pago();
        pago.setVentaId(dto.getVentaId());
        pago.setMetodoPago(dto.getMetodoPago().toUpperCase());
        pago.setMonto(dto.getMonto());
        pago.setEstado("APROBADO");
        pago.setFechaPago(LocalDateTime.now());

        return repository.save(pago);
    }

    public Pago actualizar(Long id, PagoDTO dto) {
        Pago existente = obtenerPorId(id);
        existente.setMetodoPago(dto.getMetodoPago().toUpperCase());
        existente.setMonto(dto.getMonto());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pago no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
