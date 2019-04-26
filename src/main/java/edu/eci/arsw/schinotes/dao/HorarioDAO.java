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
    public Horario getHorarioByName(String correo, String nombre) throws SchiNotesException;

    /**
     * Se obtiene el horario de un usuario dado el correo de su cuenta y el
     * id del horario.
     *
     * @param correo
     * @param id
     * @return el horario consultado por el correo y el id.
     * @throws SchiNotesException
     */
    public Horario getHorarioById(String correo, int id) throws SchiNotesException;

    /**
     * Se guarda un horario cuando un usuario lo crea.
     *
     * @param horario
     * @throws SchiNotesException
     */
    public void saveHorario(Horario horario) throws SchiNotesException;
    
    /**
     * Se consulta el horario por medio del corre electronico 
     * @param correo
     * @return
     * @throws SchiNotesException
     */
    public List<Horario> getHorarios(String correo)throws SchiNotesException;
    
    /**
     *  
     * @return
     */
    public int getMaxId();

    /**
     *  Se consulta el horario del grupo
     * @param grupo Grupo al cual se consultara el horario
     * @return
     * @throws SchiNotesException
     */
    public Horario getHorarioGrupo(int idGrupo) throws SchiNotesException;

}
