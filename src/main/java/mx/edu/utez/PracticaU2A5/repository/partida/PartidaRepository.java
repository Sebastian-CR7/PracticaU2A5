package mx.edu.utez.PracticaU2A5.repository.partida;

import mx.edu.utez.PracticaU2A5.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByJugadorIdOrderByIdDesc(Long jugadorId);
}
