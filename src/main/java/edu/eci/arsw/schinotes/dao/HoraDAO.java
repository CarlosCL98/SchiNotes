package edu.eci.arsw.schinotes.dao;

import java.util.List;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Hora;

/**
 *
 * @author carloscl
 */
public interface HoraDAO {

    /**
     * Se guardan las horas correspondientes a un horario y a cada dia de dicho
     * horario.
     *
     * @param hora
     * @param horario
     * @param dia
     * @throws SchiNotesException
     */
    public void saveHora(Hora hora, int idHorario, String dia) throws SchiNotesException;

    public List<Hora> getHorasByName(String nombreHorario) throws SchiNotesException;
    
    public List<Hora> getHorasById(int idHorario) throws SchiNotesException;

}
