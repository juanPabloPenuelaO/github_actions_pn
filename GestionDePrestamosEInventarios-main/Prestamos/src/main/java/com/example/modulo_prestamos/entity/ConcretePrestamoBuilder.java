package com.example.modulo_prestamos.entity;

import com.example.modulo_prestamos.entity.enums.Estado;
import com.example.modulo_prestamos.interfaces.PrestamoBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ConcretePrestamoBuilder implements PrestamoBuilder {
    private String usuarioId;
    private String recursoId;
    private String ubicacion;
    private String sede;
    private Estado estado;
    private LocalDateTime fechaCreacion;

    @Override
    public PrestamoBuilder setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
        return this;
    }

    @Override
    public ConcretePrestamoBuilder setRecursoId(String recursoId) {
        this.recursoId = recursoId;
        return this;
    }

    @Override
    public PrestamoBuilder setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
        return this;
    }

    @Override
    public PrestamoBuilder setSede(String sede) {
        this.sede = sede;
        return this;
    }

    @Override
    public PrestamoBuilder setEstado(Estado estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public PrestamoBuilder setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    // Método para construir el objeto de Prestamo
    @Override
    public Prestamo build() {
        return new Prestamo(usuarioId, recursoId, ubicacion, sede, estado, fechaCreacion);
    }
}

