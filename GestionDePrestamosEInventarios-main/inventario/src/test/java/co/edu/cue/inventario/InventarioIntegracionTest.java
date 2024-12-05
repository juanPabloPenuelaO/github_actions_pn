package co.edu.cue.inventario;

import co.edu.cue.inventario.ElementosDti.Computadores;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import co.edu.cue.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InventarioIntegracionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventarioRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        repository.save(new Computadores(
                "C001", "Computador Asus", "Laptop de gama media",
                TipoDeElementos.Computador, EstadosElementos.DISPONIBLE, "SedePrincipal", LocalDate.now()
        ));
        repository.save(new Computadores(
                "C002", "Computador HP", "Laptop de uso general",
                TipoDeElementos.Computador, EstadosElementos.EN_PRESTAMO, "SedePrincipal", LocalDate.now()
        ));
    }

    @Test
    void testCrearElemento() throws Exception {
        String requestBody = """
                {
                    "identificacion": "C003",
                    "nombre": "Computador Lenovo",
                    "descripcion": "Laptop para oficina",
                    "tipo": "Computador",
                    "estado": "DISPONIBLE",
                    "ubicacion": "SedePrincipal",
                    "fechaCreacion": "2024-01-12"
                }
                """;

        mockMvc.perform(post("/elementos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado", is("OK")))
                .andExpect(jsonPath("$.mensaje", is("Elemento creado exitosamente")))
                .andExpect(jsonPath("$.data.nombre", is("Computador Lenovo")));
    }

    @Test
    void testActualizarElemento() throws Exception {
        String updateRequest = """
                {
                    "nombre": "Computador Dell Actualizado",
                    "descripcion": "Laptop actualizada",
                    "estado": "DISPONIBLE",
                    "ubicacion": "Oficina Principal"
                }
                """;

        mockMvc.perform(put("/elementos/actualizar/C001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("OK")))
                .andExpect(jsonPath("$.mensaje", is("Elemento actualizado exitosamente")))
                .andExpect(jsonPath("$.data.nombre", is("Computador Dell Actualizado")))
                .andExpect(jsonPath("$.data.ubicacion", is("Oficina Principal")));
    }

    @Test
    void testFiltrarPorEstado() throws Exception {
        mockMvc.perform(get("/consultas/filtrar/estado/DISPONIBLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("OK")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].nombre", is("Computador Asus")));
    }

    @Test
    void testFiltrarPorTipo() throws Exception {
        mockMvc.perform(get("/consultas/filtrar/tipo/COMPUTADOR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("OK")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].tipo", is("Computador")));
    }

    @Test
    void testVerificarExistencia() throws Exception {
        mockMvc.perform(get("/consultas/existe/C001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("OK")))
                .andExpect(jsonPath("$.data", is(true)));

        mockMvc.perform(get("/consultas/existe/C999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.estado", is("Sin resultados")))
                .andExpect(jsonPath("$.data", is(false)));
    }

    @Test
    void testEliminarElemento() throws Exception {
        mockMvc.perform(delete("/elementos/eliminar/C001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("OK")))
                .andExpect(jsonPath("$.mensaje", is("Elemento eliminado exitosamente")));

        mockMvc.perform(get("/consultas/existe/C001"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.estado", is("Sin resultados")));
    }
}