package edu.eci.arsw.schinotes.dao;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Hora;

/**
 *
 * @author carloscl
 */
public interface HoraDao {
    

    public void saveHora(Hora hora,String horario,String dia) throws SchiNotesException;

}
