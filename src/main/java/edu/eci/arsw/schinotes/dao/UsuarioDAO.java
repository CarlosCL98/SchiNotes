package edu.eci.arsw.schinotes.dao;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Usuario;

import java.util.List;

/**
 *
 * @author carloscl
 */
public interface UsuarioDAO {
    
    public List<Usuario> loadAll() throws SchiNotesException;

    public Usuario loadUsuarioByEmail(String correo) throws SchiNotesException;
    
    public void saveUsuario(Usuario usuario) throws SchiNotesException;
    
}
