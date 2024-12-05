package com.example.modulo_prestamos.controller;

import com.example.modulo_prestamos.utils.ApiResponse;
import com.example.modulo_prestamos.entity.Prestamo;
import com.example.modulo_prestamos.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
    private final ApiResponse response;

    @Autowired
    private PrestamoService prestamoService;

    public PrestamoController() {
        this.response = new ApiResponse();
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listarPrestamos() {
        try {
            // Recupera todos los registros de Prestamos
            List<Prestamo> prestamos = prestamoService.listarPrestamos();

            // Construye la respuesta de la petición exitosa
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK,
                    "Lista de préstamos obtenida",
                    prestamos
            );
            // Retorna la respuesta exitosa junto a los datos
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Construye la respuesta de la petición fallida
            ApiResponse response = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al listar préstamos: " + e.getMessage(),
                    null
            );
            // Retorna la respuesta fallida junto a los datos
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> crearPrestamo(@RequestBody Prestamo prestamo) {
        try {
            // Crea un nuevo Prestamo
            Prestamo nuevoPrestamo = prestamoService.crearPrestamo(prestamo);

            // Construye la respuesta de la petición exitosa
            ApiResponse response = new ApiResponse(
                    HttpStatus.CREATED,
                    "Préstamo creado exitosamente",
                    nuevoPrestamo
            );
            // Retorna la respuesta exitosa junto a los datos
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Construye la respuesta de la petición fallida
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error al crear préstamo: " + e.getMessage(),
                    null
            );
            // Retorna la respuesta fallida junto a los datos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> consultarPrestamo(@PathVariable String id) {
        try {
            // Consulta un Prestamo mediante su id
            Optional<Prestamo> prestamo = prestamoService.consultarPrestamo(id);

            // Valida si encuentra el Prestamo
            if (prestamo.isPresent()) {

                // Construye la respuesta de la petición exitosa
                ApiResponse response = new ApiResponse(
                        HttpStatus.OK,
                        "Préstamo encontrado",
                        prestamo.get()
                );

                // Retorna la respuesta exitosa junto a los datos
                return ResponseEntity.ok(response);
            } else {
                // Construye la respuesta de la petición fallida cuando no encuentra el Prestamo
                ApiResponse response = new ApiResponse(
                        HttpStatus.NOT_FOUND,
                        "Préstamo no encontrado",
                        null
                );
                // Retorna la respuesta fallida junto a los datos
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            // Construye la respuesta de la petición fallida
            ApiResponse response = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al consultar préstamo: " + e.getMessage(),
                    null
            );
            // Retorna la respuesta fallida junto a los datos
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> modificarPrestamo(
            @PathVariable String id,
            @RequestBody Prestamo prestamo
    ) {
        try {
            // Modifica un Prestamo
            Prestamo prestamoModificado = prestamoService.modificarPrestamo(id, prestamo);

            // Construye la respuesta de la petición exitosa
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK,
                    "Préstamo modificado exitosamente",
                    prestamoModificado
            );

            // Retorna la respuesta exitosa junto a los datos
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Construye la respuesta de la petición fallida
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error al modificar préstamo: " + e.getMessage(),
                    null
            );
            // Retorna la respuesta fallida junto a los datos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> eliminarPrestamo(@PathVariable String id) {
        try {
            // Elimina un Prestamo
            prestamoService.eliminarPrestamo(id);

            // Construye la respuesta de la petición exitosa
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK,
                    "Préstamo eliminado exitosamente",
                    null
            );

            // Retorna la respuesta exitosa junto a los datos
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Construye la respuesta de la petición fallida
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error al eliminar préstamo: " + e.getMessage(),
                    null
            );
            // Retorna la respuesta fallida junto a los datos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/observaciones/{prestamoId}")
    public ResponseEntity<ApiResponse> agregarObservaciones(@PathVariable String prestamoId, @RequestBody String observaciones) {
        try {
            // Agrega las observaciones a un Prestamo
            prestamoService.agregarObservaciones(observaciones, prestamoId);

            // Construye la respuesta de la petición exitosa
            ApiResponse response = new ApiResponse(
                    HttpStatus.OK,
                    "Observaciones agregadas exitosamente",
                    null
            );
            // Retorna la respuesta exitosa junto a los datos
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Construye la respuesta de la petición fallida
            ApiResponse response = new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    "Error al agregar observaciones: " + e.getMessage(),
                    null
            );
            // Retorna la respuesta fallida junto a los datos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}