package edu.eci.arsw.schinotes.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.eci.arsw.schinotes.dao.GrupoDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Grupo;
import edu.eci.arsw.schinotes.model.Horario;
import edu.eci.arsw.schinotes.model.Usuario;

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
        jdbcTemplate.update(sql1,
                new Object[] { id, grupo.getNombre(), grupo.getDescripcion(), grupo.getHorarioNombre().getId() });
        String sql2 = "INSERT INTO grupo_de_trabajo (rol,grupo_identificacion,usuario_identificacion) VALUES (?,?,?)";
        jdbcTemplate.update(sql2, new Object[] { "Administrador", id, idUsuario });
    }

    @Override
    public List<Grupo> loadGroupsByUser(String correo) throws SchiNotesException {
        String sql1 = "SELECT g.identificacion,g.nombre,g.descripcion,g.tablero_nombre,g.horario_id,h.id as hid,h.nombre as hnombre "
                + "FROM Grupo g JOIN horario h ON (g.horario_id = h.id) "
                + "JOIN grupo_de_trabajo gdt ON (g.identificacion = gdt.grupo_identificacion) "
                + "JOIN Usuario u ON (gdt.usuario_identificacion = u.identificacion) WHERE u.cuenta_correo = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql1, new Object[] { correo });
        List<Grupo> grupos = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Grupo grupo = new Grupo();
            grupo.setIdentificacion((int) row.get("identificacion"));
            grupo.setNombre((String) row.get("nombre"));
            grupo.setDescripcion((String) row.get("descripcion"));
            Horario horario = new Horario();
            horario.setId((int) row.get("hid"));
            horario.setNombre((String) row.get("hnombre"));
            grupo.setHorarioNombre(horario);
            grupos.add(grupo);
        }
        if (grupos.isEmpty()) {
            throw new SchiNotesException("El usuario con correo '" + correo + "' no tiene grupos.");
        }
        return grupos;
    }

    @Override
    public Horario loadHorarioGrupo(int idGrupo) {
        String sql = "SELECT * FROM horario,grupo WHERE horario.id = grupo.horario_id AND grupo.identificacion = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { idGrupo }, new RowMapper<Horario>() {

            @Override
            public Horario mapRow(ResultSet rs, int rowNum) throws SQLException {
                Horario grupoHorario = new Horario();
                grupoHorario.setNombre(rs.getString("nombre"));
                grupoHorario.setId(rs.getInt("id"));
                Grupo grupo = new Grupo();
                grupo.setIdentificacion(rs.getInt("identificacion"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setDescripcion(rs.getString("descripcion"));
                grupo.setHorarioNombre(grupoHorario);
                grupoHorario.setGrupo(grupo);
                return grupoHorario;
            }

        });
    }

    @Override
    public void saveIntegrante(int idGrupo, Usuario integrante, Horario horario) {
        String sql = "INSERT INTO grupo_de_trabajo(rol, grupo_identificacion,usuario_identificacion) values('Integrante',"
                + idGrupo + "," + integrante.getIdentificacion() + ");";
        jdbcTemplate.update(sql);
    }
}