package mx.edu.utez.PracticaU2A5.controller;

import mx.edu.utez.PracticaU2A5.controller.dto.JugadorRequest;
import mx.edu.utez.PracticaU2A5.controller.dto.JugadorResponse;
import mx.edu.utez.PracticaU2A5.controller.dto.PartidaResponse;
import mx.edu.utez.PracticaU2A5.controller.dto.RecargaRequest;
import mx.edu.utez.PracticaU2A5.service.JugadorService;
import mx.edu.utez.PracticaU2A5.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;
    private final PartidaService partidaService;

    public JugadorController(JugadorService jugadorService, PartidaService partidaService) {
        this.jugadorService = jugadorService;
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<JugadorResponse> crear(@Valid @RequestBody JugadorRequest payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.crearJugador(payload));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.obtenerJugador(id));
    }

    @PatchMapping("/{id}/recarga")
    public ResponseEntity<JugadorResponse> recargarSaldo(@PathVariable Long id, @Valid @RequestBody RecargaRequest payload) {
        return ResponseEntity.ok(jugadorService.recargarSaldo(id, payload));
    }

    @GetMapping("/{id}/partidas")
    public ResponseEntity<List<PartidaResponse>> historial(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.obtenerHistorialPartida(id));
    }
}
