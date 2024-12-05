package co.edu.cue.inventario.Controller;

import co.edu.cue.inventario.ElementosDti.ElementosDti;
import co.edu.cue.inventario.Requests.RequestElementos;
import co.edu.cue.inventario.Requests.RespuestaApi;
import co.edu.cue.inventario.Service.ElementosService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/elementos")
public class ElementosController {
    private final ElementosService service;

    public ElementosController(ElementosService service) {
        this.service = service;
    }


    @PostMapping("/crear")
    public ResponseEntity<RespuestaApi<ElementosDti>> crearElemento(@Valid @RequestBody RequestElementos request) {
        // Llama a la funcion de crear elemento del service y el envia los parametros extrayendolos del request que
        // son los datos que se insertan desde el postman
        try {
            ElementosDti elementoCreado = service.crearElemento(
                    request.getIdentificacion(),
                    request.getNombre(),
                    request.getDescripcion(),
                    request.getTipo(),
                    request.getEstado(),
                    request.getUbicacion()
                    );
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento creado exitosamente",
                    elementoCreado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al crear el elemento",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    //Actualiza un elemento por medio del id del elemento
    // el PathVariable trae la identificacion del elemento que se quiere modificar
    // el requestBody trae los datos modificados en el POSTMAN
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> ActualizarElemento(
            @PathVariable("id") String identificacion,
            @RequestBody RequestElementos request) {
        try {
            ElementosDti actualizado = service.EditarElemento(
                    identificacion,
                    request.getNombre(),
                    request.getDescripcion(),
                    request.getEstado(),
                    request.getUbicacion()
            );
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento actualizado exitosamente",
                    actualizado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (NoSuchElementException e) {
            // Captura la excepción cuando no se encuentra el elemento
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Captura cualquier otro tipo de error general
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al actualizar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/detalles/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> verElemento(
            @PathVariable("id") String identificacion) {
        try {
            ElementosDti elementosDti = service.VerDetalles(identificacion);
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Esta es la información del elemento",
                    elementosDti // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } catch (NoSuchElementException e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al actualizar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> deleteElement(
            @PathVariable("id") String identificacion) {
        try {
            ElementosDti eliminado = service.EliminarElemento(identificacion);
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento actualizado exitosamente",
                    eliminado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al actualizar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<RespuestaApi<ElementosDti>> CambiarEstado(
            @PathVariable("id") String identificacion,
            @RequestBody RequestElementos request) {
        try {
           ElementosDti actualizado = service.CambiarEstado(
                    identificacion,
                    request.getEstado(),
                    request.getUbicacion()
                    );
            RespuestaApi<ElementosDti> response = new RespuestaApi<>(
                    "OK",
                    "Elemento actualizado exitosamente",
                    actualizado // El objeto creado se pasa directamente como `data`
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Elemento no encontrado",
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            RespuestaApi<ElementosDti> errorResponse = new RespuestaApi<>(
                    "ERROR",
                    "Error al actualizar el elemento: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
