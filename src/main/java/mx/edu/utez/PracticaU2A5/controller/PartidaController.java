package mx.edu.utez.PracticaU2A5.controller;

import mx.edu.utez.PracticaU2A5.controller.dto.IniciarPartidaRequest;
import mx.edu.utez.PracticaU2A5.controller.dto.PartidaResponse;
import mx.edu.utez.PracticaU2A5.controller.dto.TiroResponse;
import mx.edu.utez.PracticaU2A5.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<PartidaResponse> iniciar(@Valid @RequestBody IniciarPartidaRequest payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.iniciarPartida(payload));
    }

    @PostMapping("/{id}/tiro")
    public ResponseEntity<TiroResponse> realizarTiro(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.realizarTiro(id));
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<PartidaResponse> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.finalizarPartidaManual(id));
    }
}
