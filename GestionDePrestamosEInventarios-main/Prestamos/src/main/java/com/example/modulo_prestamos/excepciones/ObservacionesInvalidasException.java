package com.example.modulo_prestamos.excepciones;

public class ObservacionesInvalidasException extends RuntimeException {
    public ObservacionesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
