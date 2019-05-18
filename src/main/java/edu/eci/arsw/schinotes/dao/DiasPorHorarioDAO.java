package edu.eci.arsw.schinotes.dao;

import java.util.List;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.DiaDeLaSemana;
import edu.eci.arsw.schinotes.model.Horario;

/**
 *
 * @author carloscl
 */
public interface DiasPorHorarioDAO {

    /**
     * Se construye un horario con sus dias respectivos.
     *
     * @param horario
     * @throws SchiNotesException
     */
    public void saveDiasPorHorario(Horario horario) throws SchiNotesException;

    /**
     * Se obtienen los dias de la semana de un horario especifico.
     *
     * @param nombre
     * @return una lista con los dias de la semana del horario.
     * @throws SchiNotesException
     */
    public List<DiaDeLaSemana> getDiasByName(String nombre) throws SchiNotesException;

    public List<DiaDeLaSemana> getDiasById(int id) throws SchiNotesException;

}
