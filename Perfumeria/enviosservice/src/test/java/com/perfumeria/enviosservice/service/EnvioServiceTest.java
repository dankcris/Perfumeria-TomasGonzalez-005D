package com.perfumeria.enviosservice.service;

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

import com.perfumeria.enviosservice.client.ClientVenta;
import com.perfumeria.enviosservice.dto.EnvioDTO;
import com.perfumeria.enviosservice.dto.VentaResponse;
import com.perfumeria.enviosservice.model.Envio;
import com.perfumeria.enviosservice.repository.EnvioRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - EnvioService")
class EnvioServiceTest {

    @Mock
    private EnvioRepository repository;

    @Mock
    private ClientVenta clientVenta;

    @InjectMocks
    private EnvioService service;

    @Test
    @DisplayName("Debe crear un envío con estado PENDIENTE cuando la venta existe")
    void testCrearEnvioExitoso() {
        // Given
        EnvioDTO dto = new EnvioDTO();
        dto.setVentaId(1L);
        dto.setDireccionDestino("Av. Providencia 123");
        dto.setCiudadDestino("Santiago");
        dto.setTransportista("CHILEXPRESS");

        VentaResponse venta = new VentaResponse();
        venta.setId(1L);

        Envio envioGuardado = new Envio(1L, 1L, "Av. Providencia 123", "Santiago",
                "CHILEXPRESS", "PENDIENTE", null, "PERF-ABCD1234");

        when(clientVenta.obtenerVenta(1L)).thenReturn(venta);
        when(repository.save(any(Envio.class))).thenReturn(envioGuardado);

        // When
        Envio resultado = service.crear(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(clientVenta).obtenerVenta(1L);
    }

    @Test
    @DisplayName("Debe listar todos los envíos")
    void testListar() {
        // Given
        Envio e1 = new Envio(1L, 1L, "Calle 1", "Valparaíso", "STARKEN", "EN_CAMINO", null, "PERF-00000001");
        when(repository.findAll()).thenReturn(Arrays.asList(e1));
        // When
        List<Envio> resultado = service.listar();
        // Then
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Debe lanzar excepción si el envío no existe al buscar por id")
    void testObtenerPorIdNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());
        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
        assertTrue(ex.getMessage().contains("Envío no encontrado"));
    }

    @Test
    @DisplayName("Debe actualizar el estado de un envío correctamente")
    void testActualizarEstado() {
        // Given
        Envio existente = new Envio(1L, 1L, "Calle 1", "Santiago", "CHILEXPRESS", "PENDIENTE", null, "PERF-XYZ");
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Envio.class))).thenReturn(existente);
        // When
        Envio resultado = service.actualizarEstado(1L, "EN_CAMINO");
        // Then
        assertEquals("EN_CAMINO", resultado.getEstado());
    }

    @Test
    @DisplayName("Debe eliminar un envío existente correctamente")
    void testEliminarExitoso() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);
        // When
        assertDoesNotThrow(() -> service.eliminar(1L));
        // Then
        verify(repository).deleteById(1L);
    }
}
