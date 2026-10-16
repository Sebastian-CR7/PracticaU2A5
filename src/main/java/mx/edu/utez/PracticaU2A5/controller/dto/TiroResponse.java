package mx.edu.utez.PracticaU2A5.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.PracticaU2A5.model.EstadoPartida;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TiroResponse {

    private Long id;
    private Long partidaId;
    private Integer valorDado1;
    private Integer valorDado2;
    private Integer suma;
    private Boolean esGanador;
    private String resultado;
    private EstadoPartida estadoPartida;
    private Double saldoJugador;
}
