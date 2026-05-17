package com.perfumeria.ventasservice.service;

import com.perfumeria.ventasservice.client.ClientCliente;
import com.perfumeria.ventasservice.client.ClientPerfume;
import com.perfumeria.ventasservice.dto.VentaDTO;
import com.perfumeria.ventasservice.model.Cliente;
import com.perfumeria.ventasservice.model.Perfume;
import com.perfumeria.ventasservice.model.Venta;
import com.perfumeria.ventasservice.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}