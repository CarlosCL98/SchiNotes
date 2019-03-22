package edu.eci.arsw.SchiNotes.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.arsw.SchiNotes.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

}