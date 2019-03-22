package edu.eci.arsw.SchiNotes.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.arsw.SchiNotes.model.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {

}