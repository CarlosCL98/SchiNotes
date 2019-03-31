package edu.eci.arsw.schinotes.dao;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Actividad;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public interface ActividadDAO {

    /**
     * Se guarda una actividad dada para un usuarios con un horario especifico.
     *
     * @param actividad
     * @throws SchiNotesException
     */
    public void saveActividad(Actividad actividad) throws SchiNotesException;

}
