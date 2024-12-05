package com.example.modulo_inicio_sesion.repository;

import com.example.modulo_inicio_sesion.entities.ServiceResponse;

public interface ConexionProxy {
    ServiceResponse conectar(String correo, String pass);
}
