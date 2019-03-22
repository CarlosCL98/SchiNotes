package edu.eci.arsw.SchiNotes.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.arsw.SchiNotes.model.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {

}