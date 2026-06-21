package com.perfumeria.enviosservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfumeria.enviosservice.client.ClientVenta;
import com.perfumeria.enviosservice.dto.EnvioDTO;
import com.perfumeria.enviosservice.dto.VentaResponse;
import com.perfumeria.enviosservice.model.Envio;
import com.perfumeria.enviosservice.repository.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository repository;

    @Autowired
    private ClientVenta clientVenta;

    public List<Envio> listar() {
        return repository.findAll();
    }

    public List<Envio> listarPorVenta(Long ventaId) {
        return repository.findByVentaId(ventaId);
    }

    public List<Envio> listarPorEstado(String estado) {
        return repository.findByEstado(estado.toUpperCase());
    }

    public Envio obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con id: " + id));
    }

    public Envio crear(EnvioDTO dto) {
        // Valida que la venta existe consultando ventasservice vía Feign
        VentaResponse venta = clientVenta.obtenerVenta(dto.getVentaId());
        if (venta == null) {
            throw new RuntimeException("La venta con id " + dto.getVentaId() + " no existe.");
        }

        Envio envio = new Envio();
        envio.setVentaId(dto.getVentaId());
        envio.setDireccionDestino(dto.getDireccionDestino());
        envio.setCiudadDestino(dto.getCiudadDestino());
        envio.setTransportista(dto.getTransportista().toUpperCase());
        envio.setEstado("PENDIENTE");
        envio.setFechaEnvio(LocalDateTime.now());
        envio.setNumeroSeguimiento("PERF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        return repository.save(envio);
    }

    public Envio actualizarEstado(Long id, String nuevoEstado) {
        Envio existente = obtenerPorId(id);
        existente.setEstado(nuevoEstado.toUpperCase());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Envío no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
