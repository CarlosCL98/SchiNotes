package edu.eci.arsw.schinotes.dao;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Notificacion;
import edu.eci.arsw.schinotes.model.Usuario;

import java.util.List;

/**
 *
 * @author carloscl
 */
public interface UsuarioDAO {

    /**
     * Se consultas todos los usuarios que se han unido a la herramienta.
     *
     * @return una lista con todos los usuarios.
     * @throws SchiNotesException
     */
    public List<Usuario> loadAll() throws SchiNotesException;

    /**
     * Se consulta un usuario dado el correo de su cuenta.
     *
     * @param correo
     * @return el usuario consultado por el correo de su cuenta.
     * @throws SchiNotesException
     */
    public Usuario loadUsuarioByEmail(String correo) throws SchiNotesException;

    /**
     * Se guarda un usuario cuando este registro su respectiva cuenta.
     *
     * @param usuario
     * @throws SchiNotesException
     */
    public void saveUsuario(Usuario usuario) throws SchiNotesException;

    public List<Usuario> getAmigos (int identificacion) throws SchiNotesException;
    public void saveAmigos (int id1,int id2) throws SchiNotesException;
    public List<Usuario> deleteAmigos (String correo) throws SchiNotesException;

    public List<Usuario> loadUsuarioIncomplete(String correoPersonas) throws SchiNotesException;
    public void saveNotificaciones(Notificacion notificacion) throws SchiNotesException;

    public List<Notificacion> loadNotificaciones(String correo) throws SchiNotesException;
}
