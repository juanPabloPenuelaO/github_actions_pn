package com.example.modulo_inicio_sesion.controller;

import com.example.modulo_inicio_sesion.entities.ServiceResponse;
import com.example.modulo_inicio_sesion.repository.implementation.ConexionProxyImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class InicioSesionController {
    private final ConexionProxyImpl conexionProxy;

    // Inyección de dependencias
    public InicioSesionController(ConexionProxyImpl conexionProxy) {
        this.conexionProxy = conexionProxy;
    }

    @GetMapping("/inicio-sesion")
    public ResponseEntity<ServiceResponse> login(
            @RequestParam String correo,
            @RequestParam String pass) {

        // Llama al método de conexión a través del proxy
        ServiceResponse response = conexionProxy.conectar(correo, pass);

        // Devuelve la respuesta con el código de estado Http
        if ("FOUND".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
