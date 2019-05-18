package edu.eci.arsw.schinotes.dao;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Actividad;
import java.util.List;

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

    /**
     * Se obtiene una actividad dado el correo del usuario, el nombre del horario
     * donde está la actividad y el nombre de la actividad.
     *
     * @param correo
     * @param nombre
     * @param actividad
     * @return la actividad consultada.
     * @throws SchiNotesException
     */
    public Actividad loadActividadByCorreo(String correo, String nombre, String actividad) throws SchiNotesException;

    /**
     * Se obtienen todas las actividades de un usuario y horario específicos.
     *
     * @param correo
     * @param nombre
     * @return una lista con las actividades consultadas.
     * @throws SchiNotesException
     */
    public List<Actividad> loadAll(String correo, String nombre) throws SchiNotesException;

    /**
     * Se obtiene una actividad dado su identificador.
     * 
     * @param actividadId
     * @return una actividad
     * @throws SchiNotesException
     */
    public Actividad loadActividadById(int actividadId) throws SchiNotesException;

    /**
     * Se obtienen las actividades dado el id del horario.
     * 
     * @param idHorario
     * @return una lista con las actividades
     * @throws SchiNotesException
     */
    public List<Actividad> loadActividadesByHorarioId(int idHorario) throws SchiNotesException;

    /**
     * 
     * @param grupoId
     * @param nombreHorario
     * @return una lista con las actividades
     * @throws SchiNotesException
     */
    public List<Actividad> loadActividadesByGroup(int grupoId, String nombreHorario) throws SchiNotesException;
    
    /**
     * 
     * @param correo
     * @param idHorario
     * @param idActividad
     * @throws SchiNotesException
     */
    public void eliminarActividad(int idHorario,int idActividad) throws SchiNotesException;
}
