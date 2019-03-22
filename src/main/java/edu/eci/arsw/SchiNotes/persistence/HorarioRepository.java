package edu.eci.arsw.SchiNotes.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.eci.arsw.SchiNotes.model.Horario;
import org.springframework.stereotype.Repository;


@Repository
public interface HorarioRepository extends JpaRepository<Horario, String> {

}