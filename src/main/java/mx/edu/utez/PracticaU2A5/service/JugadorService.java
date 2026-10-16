package mx.edu.utez.PracticaU2A5.service;

import mx.edu.utez.PracticaU2A5.controller.dto.JugadorRequest;
import mx.edu.utez.PracticaU2A5.controller.dto.JugadorResponse;
import mx.edu.utez.PracticaU2A5.controller.dto.RecargaRequest;
import mx.edu.utez.PracticaU2A5.exception.customExceptions.NotFoundException;
import mx.edu.utez.PracticaU2A5.model.Jugador;
import mx.edu.utez.PracticaU2A5.repository.jugador.JugadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional
    public JugadorResponse crearJugador(JugadorRequest payload) {
        Jugador jugador = new Jugador();
        jugador.setNombre(payload.getNombre());
        jugador.setSaldo(payload.getSaldo());
        jugador.setActivo(true);

        return JugadorResponse.from(jugadorRepository.save(jugador));
    }

    @Transactional(readOnly = true)
    public JugadorResponse obtenerJugador(Long id) {
        return JugadorResponse.from(buscarJugador(id));
    }

    @Transactional
    public JugadorResponse recargarSaldo(Long id, RecargaRequest payload) {
        Jugador jugador = buscarJugador(id);
        jugador.setSaldo(jugador.getSaldo() + payload.getMonto());

        return JugadorResponse.from(jugadorRepository.save(jugador));
    }

    public Jugador buscarJugador(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El jugador con id " + id + " no existe"));
    }
}
