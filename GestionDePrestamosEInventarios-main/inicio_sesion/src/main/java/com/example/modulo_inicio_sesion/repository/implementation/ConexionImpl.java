package com.example.modulo_inicio_sesion.repository.implementation;

import com.example.modulo_inicio_sesion.entities.ServiceResponse;
import com.example.modulo_inicio_sesion.repository.ConexionProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class ConexionImpl implements ConexionProxy {
    private final RestTemplate restTemplate;

    // Ruta del servicio CUE
    @Value("${auth.external.url1}")
    private String urlCUE;

    // Ruta del servicio Usuarios
    @Value("${auth.external.url2}")
    private String urlUsuario;

    public ConexionImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ServiceResponse conectar(String correo, String pass) {
        try {
            // Construye la URL con parámetros de consulta
            String fullUrlCUE = urlCUE + "?correo=" + URLEncoder.encode(correo, StandardCharsets.UTF_8) +
                    "&pass=" + URLEncoder.encode(pass, StandardCharsets.UTF_8);
            ResponseEntity<ServiceResponse> responseEntity = restTemplate.getForEntity(
                    fullUrlCUE,
                    ServiceResponse.class
            );

            // String fullUrlUsuario = urlUsuario + "/" + URLEncoder.encode(correo, StandardCharsets.UTF_8) +
            //         "&pass=" + URLEncoder.encode(pass, StandardCharsets.UTF_8);
            // ResponseEntity<ServiceResponse> responseEntity = restTemplate.getForEntity(
            //         fullUrlCUE,
            //         ServiceResponse.class
            // );

            // Devuelve el cuerpo de la respuesta
            return responseEntity.getBody();

        } catch (Exception e) {
            // Manejo de errores
            ServiceResponse errorResponse = new ServiceResponse();
            errorResponse.setStatus("ERROR");
            errorResponse.setMensaje("Error en la conexión: " + e.getMessage());
            return errorResponse;
        }
    }
}

