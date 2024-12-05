package com.example.modulo_prestamos.interfaces;

import com.example.modulo_prestamos.entity.Prestamo;
import com.example.modulo_prestamos.entity.enums.Estado;

import java.time.LocalDateTime;

public interface PrestamoBuilder {
    PrestamoBuilder setUsuarioId(String usuarioId);
    PrestamoBuilder setRecursoId(String recursoId);
    PrestamoBuilder setUbicacion(String ubicacion);
    PrestamoBuilder setSede(String sede);
    PrestamoBuilder setEstado(Estado estado);
    PrestamoBuilder setFechaCreacion(LocalDateTime fechaCreacion);
    Prestamo build();
}
