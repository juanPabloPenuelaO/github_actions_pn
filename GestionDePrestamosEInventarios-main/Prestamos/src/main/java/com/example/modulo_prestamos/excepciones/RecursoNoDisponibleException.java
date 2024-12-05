package com.example.modulo_prestamos.excepciones;

public class RecursoNoDisponibleException extends RuntimeException {
    public RecursoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}