package com.example.modulo_prestamos.interfaces.crud;

import com.example.modulo_prestamos.entity.Prestamo;

public interface CrearObservaciones {
    Prestamo agregarObservaciones(String observaciones, String prestamoId);
}
