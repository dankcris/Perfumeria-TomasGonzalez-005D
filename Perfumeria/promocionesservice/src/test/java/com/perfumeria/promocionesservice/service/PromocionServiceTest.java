package com.perfumeria.promocionesservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfumeria.promocionesservice.dto.PromocionDTO;
import com.perfumeria.promocionesservice.model.Promocion;
import com.perfumeria.promocionesservice.repository.PromocionRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - PromocionService")
class PromocionServiceTest {

    @Mock
    private PromocionRepository repository;

    @InjectMocks
    private PromocionService service;

    @Test
    @DisplayName("Debe listar todas las promociones")
    void testListar() {
        // Given
        Promocion p1 = new Promocion(1L, "Verano", "Desc verano", 20.0, LocalDate.now(), LocalDate.now().plusDays(30), true);
        when(repository.findAll()).thenReturn(Arrays.asList(p1));
        // When
        List<Promocion> resultado = service.listar();
        // Then
        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Debe guardar una promoción con fechas válidas")
    void testGuardarExitoso() {
        // Given
        PromocionDTO dto = new PromocionDTO();
        dto.setNombre("BlackFriday");
        dto.setDescuentoPorcentaje(30.0);
        dto.setFechaInicio(LocalDate.now());
        dto.setFechaFin(LocalDate.now().plusDays(5));
        dto.setActiva(true);
        Promocion guardada = new Promocion(1L, "BlackFriday", null, 30.0, dto.getFechaInicio(), dto.getFechaFin(), true);
        when(repository.save(any())).thenReturn(guardada);
        // When
        Promocion resultado = service.guardar(dto);
        // Then
        assertNotNull(resultado);
        assertEquals("BlackFriday", resultado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar excepción si fecha fin es anterior a fecha inicio")
    void testGuardarFechaInvalida() {
        // Given
        PromocionDTO dto = new PromocionDTO();
        dto.setNombre("Promo Inválida");
        dto.setDescuentoPorcentaje(10.0);
        dto.setFechaInicio(LocalDate.now().plusDays(5));
        dto.setFechaFin(LocalDate.now()); // fecha fin anterior
        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.guardar(dto));
        assertTrue(ex.getMessage().contains("fecha de fin"));
    }

    @Test
    @DisplayName("Debe lanzar excepción al obtener promoción con id inexistente")
    void testObtenerPorIdNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());
        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
        assertTrue(ex.getMessage().contains("Promoción no encontrada"));
    }

    @Test
    @DisplayName("Debe listar solo las promociones activas")
    void testListarActivas() {
        // Given
        Promocion activa = new Promocion(1L, "Activa", "", 15.0, LocalDate.now(), LocalDate.now().plusDays(10), true);
        when(repository.findByActivaTrue()).thenReturn(Arrays.asList(activa));
        // When
        List<Promocion> resultado = service.listarActivas();
        // Then
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isActiva());
    }
}
