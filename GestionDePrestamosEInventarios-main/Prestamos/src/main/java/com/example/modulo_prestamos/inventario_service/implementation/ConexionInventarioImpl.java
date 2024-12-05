package com.example.modulo_prestamos.inventario_service.implementation;

import com.example.modulo_prestamos.inventario_service.ConexionInventarioInterface;
import com.example.modulo_prestamos.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class ConexionInventarioImpl implements ConexionInventarioInterface {
    private final RestTemplate restTemplate;

    // Variable con la ruta hacia el servicio de Inventario
    @Value("${auth.external.url}")
    private String url;

    public ConexionInventarioImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ApiResponse validarDisponibilidad(String recursoId) {
        try {
            // Construye la URL con parámetros de consulta
            String fullUrl = url + "/detalles/" + URLEncoder.encode(recursoId, StandardCharsets.UTF_8);
            ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(
                    fullUrl,
                    ApiResponse.class
            );

            // Devuelve el cuerpo de la respuesta
            return responseEntity.getBody();

        } catch (Exception e) {
            // Manejo de errores
            ApiResponse errorResponse = new ApiResponse();
            errorResponse.setStatus(HttpStatus.NOT_FOUND);
            errorResponse.setMessage("Error en la conexión: " + e.getMessage());
            return errorResponse;
        }
    }
}
