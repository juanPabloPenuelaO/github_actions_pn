package com.example.modulo_prestamos.interfaces.crud;

import com.example.modulo_prestamos.entity.Prestamo;

import java.util.Optional;

public interface Consultar {
    Optional<Prestamo> consultarPrestamo(String id);
}
