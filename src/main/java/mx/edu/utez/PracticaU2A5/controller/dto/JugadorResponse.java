package mx.edu.utez.PracticaU2A5.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.PracticaU2A5.model.Jugador;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JugadorResponse {

    private Long id;
    private String nombre;
    private Double saldo;
    private Boolean activo;

    public static JugadorResponse from(Jugador jugador) {
        return new JugadorResponse(jugador.getId(), jugador.getNombre(),
                jugador.getSaldo(), jugador.getActivo());
    }
}
