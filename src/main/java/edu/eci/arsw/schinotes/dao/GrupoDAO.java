package edu.eci.arsw.schinotes.dao;

import edu.eci.arsw.schinotes.model.Grupo;

public interface GrupoDAO {

    public int maxIdGrupo();

    public void saveGrupo(int idUsuario, Grupo grupo);
}