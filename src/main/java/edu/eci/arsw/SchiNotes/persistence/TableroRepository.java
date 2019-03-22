package edu.eci.arsw.SchiNotes.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.arsw.SchiNotes.model.Tablero;

@Repository
public interface TableroRepository extends JpaRepository<Tablero, String> {

}