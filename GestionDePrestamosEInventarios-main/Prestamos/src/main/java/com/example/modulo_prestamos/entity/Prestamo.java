package com.example.modulo_prestamos.entity;

import com.example.modulo_prestamos.entity.enums.Estado;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "prestamos")
public class Prestamo {
    @Id
    private String id;
    private String usuarioId;
    private String recursoId;
    private String ubicacion;
    private String sede;
    private String observaciones;
    private Estado estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fechaEliminacion;

    public Prestamo(String usuarioId, String recursoId, String ubicacion, String sede, Estado estado, LocalDateTime fechaCreacion) {
        this.usuarioId = usuarioId;
        this.recursoId = recursoId;
        this.ubicacion = ubicacion;
        this.sede = sede;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }
}
