package mx.edu.utez.PracticaU2A5.service;

import mx.edu.utez.PracticaU2A5.controller.dto.IniciarPartidaRequest;
import mx.edu.utez.PracticaU2A5.controller.dto.PartidaResponse;
import mx.edu.utez.PracticaU2A5.controller.dto.TiroResponse;
import mx.edu.utez.PracticaU2A5.exception.customExceptions.BadRequestException;
import mx.edu.utez.PracticaU2A5.exception.customExceptions.NotFoundException;
import mx.edu.utez.PracticaU2A5.model.EstadoPartida;
import mx.edu.utez.PracticaU2A5.model.Jugador;
import mx.edu.utez.PracticaU2A5.model.Partida;
import mx.edu.utez.PracticaU2A5.model.Tiro;
import mx.edu.utez.PracticaU2A5.repository.jugador.JugadorRepository;
import mx.edu.utez.PracticaU2A5.repository.partida.PartidaRepository;
import mx.edu.utez.PracticaU2A5.repository.tiro.TiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final TiroRepository tiroRepository;
    private final JugadorService jugadorService;

    private final Random random = new Random();

    public PartidaService(PartidaRepository partidaRepository,
                          JugadorRepository jugadorRepository,
                          TiroRepository tiroRepository,
                          JugadorService jugadorService) {
        this.partidaRepository = partidaRepository;
        this.jugadorRepository = jugadorRepository;
        this.tiroRepository = tiroRepository;
        this.jugadorService = jugadorService;
    }

    @Transactional
    public PartidaResponse iniciarPartida(IniciarPartidaRequest payload) {
        Jugador jugador = jugadorService.buscarJugador(payload.getJugadorId());

        if (!jugador.getActivo()) {
            throw new BadRequestException("El jugador con id " + jugador.getId() + " no está activo");
        }
        if (jugador.getSaldo() < payload.getApuesta()) {
            throw new BadRequestException("Saldo insuficiente para realizar la apuesta");
        }

        jugador.setSaldo(jugador.getSaldo() - payload.getApuesta());
        jugadorRepository.save(jugador);

        Partida partida = new Partida();
        partida.setJugador(jugador);
        partida.setApuesta(payload.getApuesta());
        partida.setFecha(LocalDate.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);

        return PartidaResponse.from(partidaRepository.save(partida));
    }

    @Transactional
    public TiroResponse realizarTiro(Long partidaId) {
        Partida partida = buscarPartida(partidaId);

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new BadRequestException("La partida con id " + partidaId + " no está EN_JUEGO");
        }

        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;

        boolean gana = suma == 7 || suma == 11;
        boolean pierde = suma == 2 || suma == 3 || suma == 12;
        String resultado = "CONTINUA";

        Jugador jugador = partida.getJugador();

        if (gana) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepository.save(jugador);
            resultado = "GANA";
        } else if (pierde) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            resultado = "PIERDE";
        }
        partidaRepository.save(partida);

        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(gana);
        tiro = tiroRepository.save(tiro);

        return new TiroResponse(tiro.getId(), partida.getId(), dado1, dado2, suma, gana,
                resultado, partida.getEstado(), jugador.getSaldo());
    }

    @Transactional
    public PartidaResponse finalizarPartidaManual(Long partidaId) {
        Partida partida = buscarPartida(partidaId);

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new BadRequestException("La partida con id " + partidaId + " no está EN_JUEGO");
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return PartidaResponse.from(partidaRepository.save(partida));
    }

    @Transactional(readOnly = true)
    public List<PartidaResponse> obtenerHistorialPartida(Long jugadorId) {
        jugadorService.buscarJugador(jugadorId);

        return partidaRepository.findByJugadorIdOrderByIdDesc(jugadorId)
                .stream()
                .map(PartidaResponse::from)
                .toList();
    }

    private Partida buscarPartida(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La partida con id " + id + " no existe"));
    }
}
