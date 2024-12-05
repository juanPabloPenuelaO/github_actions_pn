package com.example.modulo_inicio_sesion.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse {
    private String status;
    private String mensaje;
    private Object data;
}
