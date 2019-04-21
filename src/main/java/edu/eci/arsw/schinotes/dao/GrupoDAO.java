package edu.eci.arsw.schinotes.dao;

import java.util.List;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Grupo;

public interface GrupoDAO {

    public int maxIdGrupo();

    public void saveGrupo(int idUsuario, Grupo grupo);

    public List<Grupo> loadGroupsByUser(String correo) throws SchiNotesException;

}