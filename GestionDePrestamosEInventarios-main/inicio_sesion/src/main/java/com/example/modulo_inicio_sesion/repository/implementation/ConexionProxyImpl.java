package com.example.modulo_inicio_sesion.repository.implementation;

import com.example.modulo_inicio_sesion.entities.ServiceResponse;
import com.example.modulo_inicio_sesion.repository.ConexionProxy;
import org.springframework.stereotype.Component;

@Component
public class ConexionProxyImpl implements ConexionProxy {
    private final ConexionImpl conexion;

    public ConexionProxyImpl(ConexionImpl conexion) {
        this.conexion = conexion;
    }

    @Override
    public ServiceResponse conectar(String correo, String pass) {
        // Limpia la entrada del usuario para evitar inyecciones de SQL
        if (!correo.isEmpty() && !pass.isEmpty()) {
            correo = correo.replaceAll("[';\"\\-\\-]", "");
            pass = pass.replaceAll("[';\"\\-\\-]", "");
            return conexion.conectar(correo, pass);
        } else {
            // Manejo de errores
            ServiceResponse errorResponse = new ServiceResponse();
            errorResponse.setStatus("ERROR");
            errorResponse.setMensaje("Credenciales no pueden estar vacías");
            return errorResponse;
        }
    }

}
