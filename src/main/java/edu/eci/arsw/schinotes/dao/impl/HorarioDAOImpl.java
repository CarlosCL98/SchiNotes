package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.dao.UsuarioDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Horario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import edu.eci.arsw.schinotes.dao.HorarioDAO;

/**
 *
 * @author carloscl
 */
@Repository
public class HorarioDAOImpl implements HorarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Horario> getHorarios(String correo) throws SchiNotesException {
        String query = "SELECT h.id as hid,h.nombre as hnombre,h.usuario_identificacion,u.identificacion,u.nombre as unombre,u.apellido,u.cuenta_correo FROM horario h JOIN usuario u ON(h.usuario_identificacion = u.identificacion) WHERE u.cuenta_correo = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[] { correo });
        List<Horario> horarios = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Horario horario = new Horario();
            horario.setId((int) row.get("hid"));
            horario.setNombre((String) row.get("hnombre"));
            Usuario usuario = new Usuario();
            usuario.setIdentificacion((int) row.get("identificacion"));
            usuario.setNombre((String) row.get("unombre"));
            usuario.setApellido((String) row.get("apellido"));
            horario.setUsuario(usuario);

            horarios.add(horario);
        }
        if (horarios.isEmpty()) {
            throw new SchiNotesException("No hay horarios existentes.");
        }
        return horarios;
    }

    @Override
    public Horario getHorarioByName(String correo, String nombre) throws SchiNotesException {
        String query = "SELECT h.id as hid,h.nombre as hnombre,h.usuario_identificacion,u.identificacion,u.nombre as unombre,u.apellido,u.cuenta_correo FROM horario h JOIN usuario u ON(h.usuario_identificacion = u.identificacion) WHERE h.nombre = ? and u.cuenta_correo = ?";

        return (Horario) jdbcTemplate.queryForObject(query, new Object[] { nombre, correo }, new RowMapper<Horario>() {
            @Override
            public Horario mapRow(ResultSet rs, int rwNumber) throws SQLException {
                Horario horario = new Horario();
                horario.setId(rs.getInt("hid"));
                horario.setNombre(rs.getString("hnombre"));
                Usuario usuario = new Usuario();
                usuario.setIdentificacion(rs.getInt("identificacion"));
                usuario.setNombre(rs.getString("unombre"));
                usuario.setApellido(rs.getString("apellido"));
                horario.setUsuario(usuario);
                return horario;
            }
        });
    }

    @Override
    public Horario getHorarioById(String correo, int id) throws SchiNotesException {
        String query = "SELECT h.id as hid,h.nombre as hnombre,h.usuario_identificacion,u.identificacion,u.nombre as unombre,u.apellido,u.cuenta_correo FROM horario h JOIN usuario u ON(h.usuario_identificacion = u.identificacion) WHERE h.id = ? and u.cuenta_correo = ?";

        return (Horario) jdbcTemplate.queryForObject(query, new Object[] { id, correo }, new RowMapper<Horario>() {
            @Override
            public Horario mapRow(ResultSet rs, int rwNumber) throws SQLException {
                Horario horario = new Horario();
                horario.setId(rs.getInt("hid"));
                horario.setNombre(rs.getString("hnombre"));
                Usuario usuario = new Usuario();
                usuario.setIdentificacion(rs.getInt("identificacion"));
                usuario.setNombre(rs.getString("unombre"));
                usuario.setApellido(rs.getString("apellido"));
                horario.setUsuario(usuario);
                return horario;
            }
        });
    }

    @Override
    public void saveHorario(Horario horario) throws SchiNotesException {
        int id = getMaxId();
        String query = "INSERT INTO horario (id,nombre,usuario_identificacion) VALUES(?,?,?)";

        jdbcTemplate.update(query, new Object[] { 
            id+1, horario.getNombre(), horario.getUsuario().getIdentificacion() 
        });
    }

    @Override
    public int getMaxId() {
        String maxIdQuery = "SELECT CASE WHEN MAX(h.id) is NULL THEN 0 ELSE MAX(h.id) END FROM horario h";
        int id = jdbcTemplate.queryForObject(maxIdQuery, Integer.class);
        return id;
    }

}
