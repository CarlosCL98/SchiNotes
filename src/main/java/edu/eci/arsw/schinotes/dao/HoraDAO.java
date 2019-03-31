package edu.eci.arsw.schinotes.dao;

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
    public void saveHora(Hora hora, String horario, String dia) throws SchiNotesException;

}
