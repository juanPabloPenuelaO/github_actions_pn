package co.edu.cue.inventario.Controller;


import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Enums.EstadosElementos;
import co.edu.cue.inventario.Enums.TipoDeElementos;
import co.edu.cue.inventario.Requests.RespuestaApi;
import co.edu.cue.inventario.Service.ConsultasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/consultas")
public class ConsultasController {
    private final ConsultasService service;

    public ConsultasController(ConsultasService service) {this.service = service;}


    @GetMapping("/filtrar/tipo/{tipo}")
    public ResponseEntity<RespuestaApi<List<ElementosDti>>> filtrarPorTipo(
            @PathVariable("tipo") TipoDeElementos tipo) {
        try {
            List<ElementosDti> elementos = service.filtrarPorTipo(tipo);
            if (elementos.isEmpty()) {
                RespuestaApi<List<ElementosDti>> response = new RespuestaApi<>(
                        "Sin resultados",
                        "No hay elementos de ese tipo",
                        null // El objeto creado se pasa directamente como `data`
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            RespuestaApi<List<ElementosDti>> response = new RespuestaApi<>(
                    "OK",
                    "Estos son los elementos regsitardos con tipo "+tipo,
                    elementos // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            RespuestaApi<List<ElementosDti>> response = new RespuestaApi<>(
                    "Error",
                    "Error al buscar elementos de tipo "+tipo,
                    null // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/filtrar/estado/{estado}")
    public ResponseEntity<RespuestaApi<List<ElementosDti>>> filtrarPorEstado(
            @PathVariable("estado") EstadosElementos estado) {
        try {
            List<ElementosDti> elementos = service.filtrarPorEstado(estado);
            if (elementos.isEmpty()) {
                RespuestaApi<List<ElementosDti>> response = new RespuestaApi<>(
                        "Sin resultados",
                        "No hay elementos de en este estado",
                        null // El objeto creado se pasa directamente como `data`
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            RespuestaApi<List<ElementosDti>> response = new RespuestaApi<>(
                    "OK",
                    "Estos son los elementos en estado "+estado,
                    elementos // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            RespuestaApi<List<ElementosDti>> response = new RespuestaApi<>(
                    "Error",
                    "Hubo un error durante la busqueda",
                    null // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<RespuestaApi<Boolean>> verificarExistencia(
            @PathVariable("id") String id) {
        try {
            Boolean existe = service.existeElemento(id);
            if (existe) {
                RespuestaApi<Boolean> response = new RespuestaApi<>(
                        "OK",
                        "El elemento con identificación " + id + " si existe",
                        existe // El objeto creado se pasa directamente como `data`
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            RespuestaApi<Boolean> response = new RespuestaApi<>(
                    "Sin resultados",
                    "No existe el elemento con identificacion: " + id,
                    false // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            RespuestaApi<Boolean> response = new RespuestaApi<>(
                    "Error",
                    "Hubo un error durante la busqueda",
                    null // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

