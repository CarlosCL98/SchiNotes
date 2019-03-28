package edu.eci.arsw.schinotes.dao;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Horario;

/**
 *
 * @author carloscl
 */
public interface HorarioDao {

    public Horario getHorario(String correo,String nombre) throws SchiNotesException;

    public void saveHorario(Horario horario) throws SchiNotesException;

}
