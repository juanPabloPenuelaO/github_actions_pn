package com.example.modulo_prestamos.interfaces.crud;


import com.example.modulo_prestamos.entity.Prestamo;

public interface Modificar {
    Prestamo modificarPrestamo(String id, Prestamo prestamo);
}
