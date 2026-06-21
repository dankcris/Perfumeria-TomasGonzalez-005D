package com.perfumeria.ventasservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfumeria.ventasservice.client.ClientCliente;
import com.perfumeria.ventasservice.client.ClientPerfume;
import com.perfumeria.ventasservice.dto.VentaDTO;
import com.perfumeria.ventasservice.model.Cliente;
import com.perfumeria.ventasservice.model.Perfume;
import com.perfumeria.ventasservice.model.Venta;
import com.perfumeria.ventasservice.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository repository;

    @Autowired
    private ClientCliente clientCliente;

    @Autowired
    private ClientPerfume clientPerfume;

    public Venta guardar(VentaDTO dto){

        Cliente cliente =
                clientCliente.obtenerCliente(dto.getClienteId());

        Perfume perfume =
                clientPerfume.obtenerPerfume(dto.getPerfumeId());

        double total =
                perfume.getPrecio() * dto.getCantidad();

        Venta venta = new Venta();

        venta.setClienteId(cliente.getId());
        venta.setPerfumeId(perfume.getId());
        venta.setCantidad(dto.getCantidad());
        venta.setTotal(total);

        return repository.save(venta);
    }

    public List<Venta> listar() {
        return repository.findAll();
    }

    public Venta obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
    }

    public Venta actualizar(Long id, VentaDTO dto) {

        Venta ventaExistente = obtenerPorId(id);

        Cliente cliente = clientCliente.obtenerCliente(dto.getClienteId());
        Perfume perfume = clientPerfume.obtenerPerfume(dto.getPerfumeId());

        double total = perfume.getPrecio() * dto.getCantidad();

        ventaExistente.setClienteId(cliente.getId());
        ventaExistente.setPerfumeId(perfume.getId());
        ventaExistente.setCantidad(dto.getCantidad());
        ventaExistente.setTotal(total);

        return repository.save(ventaExistente);
    }

    public void eliminar(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        }

        repository.deleteById(id);
    }
}
