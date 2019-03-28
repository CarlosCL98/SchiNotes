package edu.eci.arsw.schinotes.dao;

import java.util.List;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.DiaDeLaSemana;
import edu.eci.arsw.schinotes.model.Hora;
import edu.eci.arsw.schinotes.model.Horario;

/**
 *
 * @author carloscl
 */
public interface DiasPorHorarioDAO {

    public void saveDiasPorHorario(Horario horario) throws SchiNotesException;

    public List<DiaDeLaSemana> getDias(String nombre) throws SchiNotesException;

}
