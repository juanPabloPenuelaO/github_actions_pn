package com.example.modulo_prestamos;

import com.example.modulo_prestamos.entity.Prestamo;
import com.example.modulo_prestamos.entity.enums.Estado;
import com.example.modulo_prestamos.excepciones.PrestamoInvalidoException;
import com.example.modulo_prestamos.interfaces.PrestamoBuilder;
import com.example.modulo_prestamos.repository.PrestamoRepository;
import com.example.modulo_prestamos.service.PrestamoService;
import com.example.modulo_prestamos.service.PrestamoValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrestamoServiceTest {

    // Mocks de dependencias para aislar el comportamiento del servicio
    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private PrestamoBuilder builder;

    @Mock
    private PrestamoValidationService validationService;

    // Instancia del servicio a probar con mocks inyectados
    @InjectMocks
    private PrestamoService prestamoService;

    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
        prestamo = new Prestamo();
        prestamo.setId("123");
        prestamo.setUsuarioId("user1");
        prestamo.setRecursoId("recurso1");
        prestamo.setUbicacion("Biblioteca Central");
        prestamo.setSede("Sede Principal");
    }

    /**
     * Prueba para consultarPrestamo() - Caso de préstamo existente
     * Verifica que:
     * 1. El método devuelve un Optional con el préstamo correcto
     * 2. El préstamo recuperado coincide con el esperado
     */
    @Test
    void consultarPrestamo_PrestamoExistente() {
        when(prestamoRepository.findById("123")).thenReturn(Optional.of(prestamo));

        Optional<Prestamo> resultado = prestamoService.consultarPrestamo("123");

        assertTrue(resultado.isPresent());
        assertEquals(prestamo, resultado.get());
    }

    /**
     * Prueba para consultarPrestamo() - Caso de préstamo no existente
     * Verifica que:
     * 1. El método devuelve un Optional vacío
     * 2. No se devuelve ningún préstamo para un ID inexistente
     */
    @Test
    void consultarPrestamo_PrestamoNoExistente() {
        when(prestamoRepository.findById("456")).thenReturn(Optional.empty());

        Optional<Prestamo> resultado = prestamoService.consultarPrestamo("456");

        assertTrue(resultado.isEmpty());
    }

    /**
     * Prueba para listarPrestamos()
     * Verifica que:
     * 1. El método devuelve la lista completa de préstamos
     * 2. El número de préstamos es correcto
     * 3. La lista contiene los préstamos esperados
     */
    @Test
    void listarPrestamos() {
        List<Prestamo> prestamos = Arrays.asList(prestamo, new Prestamo());
        when(prestamoRepository.findAll()).thenReturn(prestamos);

        List<Prestamo> resultado = prestamoService.listarPrestamos();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(prestamo));
    }

    /**
     * Prueba para crearPrestamo() - Caso de préstamo válido
     * Verifica que:
     * 1. El método valida el préstamo antes de crearlo
     * 2. Se construye un nuevo préstamo
     * 3. El préstamo se guarda en el repositorio
     * 4. Se devuelve el préstamo creado
     */
    @Test
    void crearPrestamo_PrestamoValido() {
        Prestamo prestamoNuevo = new Prestamo();
        prestamoNuevo.setUsuarioId("user1");

        when(builder.setUsuarioId(any())).thenReturn(builder);
        when(builder.setRecursoId(any())).thenReturn(builder);
        when(builder.setUbicacion(any())).thenReturn(builder);
        when(builder.setSede(any())).thenReturn(builder);
        when(builder.setEstado(any())).thenReturn(builder);
        when(builder.setFechaCreacion(any())).thenReturn(builder);
        when(builder.build()).thenReturn(prestamoNuevo);
        when(prestamoRepository.save(any())).thenReturn(prestamoNuevo);

        doNothing().when(validationService).validarPrestamo(any());

        Prestamo resultado = prestamoService.crearPrestamo(prestamoNuevo);

        assertNotNull(resultado);
        verify(validationService).validarPrestamo(any());
        verify(prestamoRepository).save(any());
    }

    /**
     * Prueba para modificarPrestamo() - Caso de préstamo existente
     * Verifica que:
     * 1. Se puede modificar un préstamo existente
     * 2. Los datos se actualizan correctamente
     * 3. Se establece la fecha de modificación
     * 4. Se valida el préstamo antes de modificarlo
     */
    @Test
    void modificarPrestamo_PrestamoExistente() {
        Prestamo prestamoExistente = new Prestamo();
        prestamoExistente.setId("123");

        when(prestamoRepository.findById("123")).thenReturn(Optional.of(prestamoExistente));
        when(prestamoRepository.save(any())).thenReturn(prestamoExistente);
        doNothing().when(validationService).validarPrestamo(any());

        Prestamo prestamoActualizado = new Prestamo();
        prestamoActualizado.setUsuarioId("user2");

        Prestamo resultado = prestamoService.modificarPrestamo("123", prestamoActualizado);

        assertNotNull(resultado);
        assertEquals("user2", resultado.getUsuarioId());
        assertNotNull(resultado.getFechaModificacion());
        verify(validationService).validarPrestamo(any());
    }

    /**
     * Prueba para modificarPrestamo() - Caso de préstamo no existente
     * Verifica que:
     * 1. Se lanza una excepción al intentar modificar un préstamo inexistente
     * 2. No se realiza ninguna modificación
     */
    @Test
    void modificarPrestamo_PrestamoNoExistente() {
        when(prestamoRepository.findById("456")).thenReturn(Optional.empty());

        Prestamo prestamoActualizado = new Prestamo();

        assertThrows(PrestamoInvalidoException.class, () -> {
            prestamoService.modificarPrestamo("456", prestamoActualizado);
        });
    }

    /**
     * Prueba para eliminarPrestamo() - Caso de préstamo existente
     * Verifica que:
     * 1. El préstamo cambia a estado INACTIVO
     * 2. Se establece la fecha de eliminación
     * 3. Se guarda el préstamo modificado en el repositorio
     */
    @Test
    void eliminarPrestamo_PrestamoExistente() {
        // Crea un nuevo prestamo y le coloca el id y el estado
        Prestamo prestamoExistente = new Prestamo();
        prestamoExistente.setId("123");
        prestamoExistente.setEstado(Estado.ACTIVO);

        // Busca el
        when(prestamoRepository.findById("123")).thenReturn(Optional.of(prestamoExistente));
        when(prestamoRepository.save(any())).thenReturn(prestamoExistente);

        prestamoService.eliminarPrestamo("123");


        assertEquals(Estado.INACTIVO, prestamoExistente.getEstado());
        assertNotNull(prestamoExistente.getFechaEliminacion());
        verify(prestamoRepository).save(prestamoExistente);
    }

    /**
     * Prueba para eliminarPrestamo() - Caso de préstamo no existente
     * Verifica que:
     * 1. Se lanza una excepción al intentar eliminar un préstamo inexistente
     * 2. No se realiza ninguna operación de eliminación
     */
    @Test
    void eliminarPrestamo_PrestamoNoExistente() {
        // Busca el prestamo con el id 1000000 y espera retornar vacio
        when(prestamoRepository.findById("1000000")).thenReturn(Optional.empty());

        // Debe responder con una excepcion
        assertThrows(PrestamoInvalidoException.class, () -> {
            prestamoService.eliminarPrestamo("1000000");
        });
    }

    /**
     * Prueba para agregarObservaciones() - Caso de préstamo existente
     * Verifica que:
     * 1. Se pueden agregar observaciones a un préstamo existente
     * 2. Las observaciones se guardan correctamente
     * 3. Se validan las observaciones antes de agregarlas
     */
    @Test
    void agregarObservaciones_PrestamoExistente() {
        Prestamo prestamoExistente = new Prestamo();
        prestamoExistente.setId("123");

        when(prestamoRepository.findById("123")).thenReturn(Optional.of(prestamoExistente));
        when(prestamoRepository.save(any())).thenReturn(prestamoExistente);
        doNothing().when(validationService).validarObservaciones(any(), any());

        Prestamo resultado = prestamoService.agregarObservaciones("Observaciones de prueba", "123");

        assertEquals("Observaciones de prueba", resultado.getObservaciones());
        verify(validationService).validarObservaciones(any(), any());
        verify(prestamoRepository).save(prestamoExistente);
    }
}