package edu.eci.arsw.schinotes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.eci.arsw.schinotes.dao.GrupoDAO;
import edu.eci.arsw.schinotes.model.Grupo;

@Repository
public class GrupoDAOImpl implements GrupoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int maxIdGrupo() {
        String sql = "SELECT CASE WHEN MAX(g.identificacion) is NULL THEN 0 ELSE MAX(g.identificacion) END FROM Grupo g";
        int id = jdbcTemplate.queryForObject(sql, Integer.class);
        return id;
    }

    @Override
    public void saveGrupo(int idUsuario, Grupo grupo) {
        String sql1 = "INSERT INTO Grupo (identificacion,nombre,descripcion,horario_id) VALUES (?,?,?,?)";
        int id = maxIdGrupo() + 1;
        jdbcTemplate.update(sql1, new Object[]{
            id, grupo.getNombre(), grupo.getDescripcion(), grupo.getHorarioNombre().getId()
        });
        String sql2 = "INSERT INTO grupo_de_trabajo (rol,grupo_identificacion,usuario_identificacion) VALUES (?,?,?)";
        jdbcTemplate.update(sql2, new Object[]{
            "Administrador", id, idUsuario
        });
    }

}