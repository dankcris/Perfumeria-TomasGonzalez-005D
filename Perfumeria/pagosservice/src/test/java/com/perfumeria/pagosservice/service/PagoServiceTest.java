package com.perfumeria.pagosservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfumeria.pagosservice.client.ClientVenta;
import com.perfumeria.pagosservice.dto.PagoDTO;
import com.perfumeria.pagosservice.dto.VentaResponse;
import com.perfumeria.pagosservice.model.Pago;
import com.perfumeria.pagosservice.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - PagoService")
class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private ClientVenta clientVenta;

    @InjectMocks
    private PagoService service;

    @Test
    @DisplayName("Debe registrar un pago correctamente cuando la venta existe")
    void testRegistrarPagoExitoso() {
        // Given
        PagoDTO dto = new PagoDTO();
        dto.setVentaId(1L);
        dto.setMetodoPago("TARJETA");
        dto.setMonto(25000.0);

        VentaResponse venta = new VentaResponse();
        venta.setId(1L);
        venta.setTotal(25000.0);

        Pago pagoGuardado = new Pago(1L, 1L, "TARJETA", 25000.0, "APROBADO", null);

        when(clientVenta.obtenerVenta(1L)).thenReturn(venta);
        when(repository.save(any(Pago.class))).thenReturn(pagoGuardado);

        // When
        Pago resultado = service.registrar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("APROBADO", resultado.getEstado());
        verify(clientVenta).obtenerVenta(1L);
    }

    @Test
    @DisplayName("Debe listar todos los pagos")
    void testListar() {
        // Given
        Pago p1 = new Pago(1L, 1L, "EFECTIVO", 10000.0, "APROBADO", null);
        when(repository.findAll()).thenReturn(Arrays.asList(p1));
        // When
        List<Pago> resultado = service.listar();
        // Then
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Debe lanzar excepción si el pago no existe al buscar por id")
    void testObtenerPorIdNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());
        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
        assertTrue(ex.getMessage().contains("Pago no encontrado"));
    }

    @Test
    @DisplayName("Debe eliminar un pago existente correctamente")
    void testEliminarExitoso() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        // When
        assertDoesNotThrow(() -> service.eliminar(1L));
        // Then
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe listar pagos por ventaId")
    void testListarPorVenta() {
        // Given
        Pago p = new Pago(1L, 2L, "TRANSFERENCIA", 50000.0, "APROBADO", null);
        when(repository.findByVentaId(2L)).thenReturn(Arrays.asList(p));
        // When
        List<Pago> resultado = service.listarPorVenta(2L);
        // Then
        assertEquals(1, resultado.size());
        assertEquals(2L, resultado.get(0).getVentaId());
    }
}
