package com.example.modulo_prestamos;

import com.example.modulo_prestamos.entity.Prestamo;
import com.example.modulo_prestamos.entity.enums.Estado;
import com.example.modulo_prestamos.repository.PrestamoRepository;
import com.example.modulo_prestamos.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class PrestamoIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @MockBean
    private com.example.modulo_prestamos.inventario_service.ConexionInventarioInterface inventarioService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        prestamoRepository.deleteAll(); // Limpia la base de datos antes de cada prueba
    }

    @Test
    void testCrearPrestamoConInventarioDisponible() throws Exception {
        // Simular la respuesta del servicio de inventario
        ApiResponse inventarioResponse = new ApiResponse();
        inventarioResponse.setStatus(org.springframework.http.HttpStatus.OK);
        inventarioResponse.setMessage("Recurso disponible");
        when(inventarioService.validarDisponibilidad("recurso-1")).thenReturn(inventarioResponse);

        // JSON de solicitud para crear un préstamo
        String prestamoJson = """
            {
                "usuarioId": "usuario-1",
                "recursoId": "recurso-1",
                "ubicacion": "salon 204",
                "sede": "Sede Principal"
            }
            """;

        // Realiza la solicitud POST al endpoint de creación de préstamo
        mockMvc.perform(post("/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(prestamoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.message").value("Préstamo creado exitosamente"))
                .andExpect(jsonPath("$.data.usuarioId").value("usuario-1"))
                .andExpect(jsonPath("$.data.recursoId").value("recurso-1"));

        // Verifica que el préstamo fue almacenado en la base de datos
        Prestamo prestamoGuardado = prestamoRepository.findAll().get(0);
        assert prestamoGuardado.getUsuarioId().equals("usuario-1");
        assert prestamoGuardado.getRecursoId().equals("recurso-1");
        assert prestamoGuardado.getEstado() == Estado.ACTIVO;

        // Verifica que se llamó al servicio de inventario
        verify(inventarioService, times(1)).validarDisponibilidad("recurso-1");
    }

    @Test
    void testCrearPrestamoConInventarioNoDisponible() throws Exception {
        // Simular la respuesta del servicio de inventario indicando que no hay disponibilidad
        ApiResponse inventarioResponse = new ApiResponse();
        inventarioResponse.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
        inventarioResponse.setMessage("Recurso no disponible");
        when(inventarioService.validarDisponibilidad("recurso-2")).thenReturn(inventarioResponse);

        // JSON de solicitud para crear un préstamo
        String prestamoJson = """
            {
                "usuarioId": "usuario-2",
                "recursoId": "recurso-2",
                "ubicacion": "sala sistemas 1",
                "sede": "Sede Principal"
            }
            """;

        // Realiza la solicitud POST al endpoint de creación de préstamo
        mockMvc.perform(post("/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(prestamoJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Error al crear préstamo: Recurso no disponible para préstamo"));

        // Verifica que no se creó ningún préstamo en la base de datos
        assert prestamoRepository.findAll().isEmpty();

        // Verifica que se llamó al servicio de inventario
        verify(inventarioService, times(1)).validarDisponibilidad("recurso-2");
    }
}
