package mx.edu.utez.PracticaU2A5.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IniciarPartidaRequest {

    @NotNull(message = "El id del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @Positive(message = "La apuesta debe ser mayor a 0")
    private Double apuesta;
}
