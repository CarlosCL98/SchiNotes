package edu.eci.arsw.schinotes.dao;

import java.util.List;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Horario;

/**
 *
 * @author carloscl
 */
public interface HorarioDAO {

    /**
     * Se obtiene el horario de un usuario dado el correo de su cuenta y el
     * nombre del horario.
     *
     * @param correo
     * @param nombre
     * @return el horario consultado por el correo y el nombre.
     * @throws SchiNotesException
     */
    public Horario getHorario(String correo, String nombre) throws SchiNotesException;

    /**
     * Se guarda un horario cuando un usuario lo crea.
     *
     * @param horario
     * @throws SchiNotesException
     */
    public void saveHorario(Horario horario) throws SchiNotesException;

	public List<Horario> getHorarios(String correo)throws SchiNotesException;

}
