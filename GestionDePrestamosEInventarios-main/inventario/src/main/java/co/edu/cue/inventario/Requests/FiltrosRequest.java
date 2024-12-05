package co.edu.cue.inventario.Requests;

import co.edu.cue.inventario.Enums.TipoDeElementos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FiltrosRequest {
    @NotNull(message = "El tipo es obligatorio")
    private TipoDeElementos tipo;

}
