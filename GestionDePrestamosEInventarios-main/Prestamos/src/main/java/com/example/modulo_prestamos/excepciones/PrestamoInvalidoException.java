package com.example.modulo_prestamos.excepciones;

public class PrestamoInvalidoException extends RuntimeException {
    public PrestamoInvalidoException(String mensaje) {
        super(mensaje);
    }
}