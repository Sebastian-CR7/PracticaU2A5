package mx.edu.utez.PracticaU2A5.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.PracticaU2A5.model.EstadoPartida;
import mx.edu.utez.PracticaU2A5.model.Partida;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartidaResponse {

    private Long id;
    private LocalDate fecha;
    private EstadoPartida estado;
    private Long jugadorId;
    private Double apuesta;

    public static PartidaResponse from(Partida partida) {
        return new PartidaResponse(partida.getId(), partida.getFecha(), partida.getEstado(),
                partida.getJugador().getId(), partida.getApuesta());
    }
}
