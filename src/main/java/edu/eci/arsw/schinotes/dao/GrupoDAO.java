package edu.eci.arsw.schinotes.dao;

import java.util.List;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Grupo;
import edu.eci.arsw.schinotes.model.Horario;
import edu.eci.arsw.schinotes.model.Usuario;

/**
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public interface GrupoDAO {

    public int maxIdGrupo();

    public void saveGrupo(int idUsuario, Grupo grupo);

    public List<Grupo> loadGroupsByUser(String correo) throws SchiNotesException;

    public Horario loadHorarioGrupo(int idGrupo);

	public void saveIntegrante(int idGrupo, Usuario integrante, Horario horario);

    public List<Grupo> getAllGroups() throws SchiNotesException;

	public void deleteUserFromGroup(int grupoId, String correo) throws SchiNotesException;
    
}