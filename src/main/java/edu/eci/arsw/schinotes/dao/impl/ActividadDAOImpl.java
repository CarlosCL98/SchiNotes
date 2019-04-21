package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.dao.ActividadDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Actividad;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@Repository
public class ActividadDAOImpl implements ActividadDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveActividad(Actividad actividad) throws SchiNotesException {
        String sql1 = "SELECT CASE WHEN MAX(id) is NULL THEN 0 ELSE MAX(id) END FROM Actividad";
        int id = jdbcTemplate.queryForObject(sql1, Integer.class);
        String sql2 = "INSERT INTO Actividad (id,nombre,descripcion,fecha_creacion,hora_hora,hora_fin,Hora_Dias_Por_Horario_Horario_id,hora_dias_por_horario_dia_nombre) VALUES (?,?,?,TO_DATE(?,'YYYY-MM-DD'),TO_TIMESTAMP(?,'HH24:MI:SS'),TO_TIMESTAMP(?,'HH24:MI:SS'),?,?)";
        jdbcTemplate.update(sql2,
                new Object[] { id + 1, actividad.getNombre(), actividad.getDescripcion(), actividad.getFecha(),
                        actividad.getHora_ini(), actividad.getHora_fin(), actividad.getHorario_id(),
                        actividad.getDia() });
        String sql3 = "INSERT INTO actividad_por_horario (actividad_id,horario_id) VALUES (?,?)";
        jdbcTemplate.update(sql3, new Object[] { id + 1, actividad.getHorario_id() });
    }

    @Override
    public Actividad loadActividad(String correo, String nombre, String actividad) throws SchiNotesException {
        String sql = "SELECT a.id,a.nombre,a.descripcion,to_char(a.fecha_creacion,'YYYY-MM-DD') as fecha_creacion,to_char(a.hora_hora, 'HH24:MI:SS') as hora_hora,a.hora_dias_por_horario_dia_nombre,a.hora_dias_por_horario_horario_id, to_char(a.hora_fin, 'HH24:MI:SS') as hora_fin\n"
                + "FROM usuario u JOIN horario h ON (u.identificacion=h.usuario_identificacion) \n"
                + "JOIN actividad_por_horario aph ON (h.id=aph.horario_id)\n"
                + "JOIN actividad a ON (a.id=aph.actividad_id)\n"
                + "WHERE u.cuenta_correo = ? AND h.nombre = ? AND a.nombre = ?;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { correo, nombre, actividad });
        if (rows.isEmpty()) {
            throw new SchiNotesException("La actividad '" + actividad + "' del usuario con correo '" + correo
                    + "' y en el horario '" + nombre + "' no existe.");
        }
        return (Actividad) jdbcTemplate.queryForObject(sql, new Object[] { correo, nombre, actividad },
                new RowMapper<Actividad>() {
                    @Override
                    public Actividad mapRow(ResultSet rs, int rwNumber) throws SQLException {
                        Actividad actividad = new Actividad();
                        actividad.setId(rs.getInt("id"));
                        actividad.setNombre(rs.getString("nombre"));
                        actividad.setDescripcion(rs.getString("descripcion"));
                        actividad.setFecha((String) rs.getObject("fecha_creacion"));
                        actividad.setHorario_id(rs.getInt("hora_dias_por_horario_horario_id"));
                        actividad.setDia(rs.getString("hora_dias_por_horario_dia_nombre"));
                        actividad.setHora_ini(rs.getString("hora_hora"));
                        actividad.setHora_fin(rs.getString("hora_fin"));
                        return actividad;
                    }
                });
    }

    @Override
    public List<Actividad> loadAll(String correo, String nombre) throws SchiNotesException {
        String sql = "SELECT a.id,a.nombre,a.descripcion,to_char(a.fecha_creacion,'YYYY-MM-DD') as fecha_creacion,to_char(a.hora_hora, 'HH24:MI:SS') as hora_hora,a.hora_dias_por_horario_dia_nombre,a.hora_dias_por_horario_horario_id,to_char(a.hora_fin, 'HH24:MI:SS') as hora_fin\n"
                + "FROM usuario u JOIN horario h ON (u.identificacion=h.usuario_identificacion) \n"
                + "JOIN actividad_por_horario aph ON (h.id=aph.horario_id)\n"
                + "JOIN actividad a ON (a.id=aph.actividad_id)\n"
                + "WHERE u.cuenta_correo = ? AND h.nombre = ? order by a.hora_dias_por_horario_dia_nombre";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { correo, nombre });
        List<Actividad> actividades = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Actividad actividad = new Actividad();
            actividad.setId((int) row.get("id"));
            actividad.setNombre((String) row.get("nombre"));
            actividad.setDescripcion((String) row.get("descripcion"));
            actividad.setFecha((String) row.get("fecha_creacion"));
            actividad.setHorario_id((int) row.get("hora_dias_por_horario_horario_id"));
            actividad.setDia((String) row.get("hora_dias_por_horario_dia_nombre"));
            actividad.setHora_ini((String) row.get("hora_hora"));
            actividad.setHora_fin((String) row.get("hora_fin"));
            actividades.add(actividad);
        }
        if (actividades.isEmpty()) {
            throw new SchiNotesException(
                    "El usuario con correo '" + correo + "' y en el horario '" + nombre + "' no tiene actividades.");
        }
        return actividades;
    }

    @Override
    public Actividad loadActividadById(int actividadId) throws SchiNotesException {
        String sql1 = "SELECT a.id,a.nombre,a.descripcion,to_char(a.fecha_creacion,'YYYY-MM-DD') as fecha_creacion,to_char(a.hora_fin, 'HH24:MI:SS') as hora_fin,to_char(a.hora_hora, 'HH24:MI:SS') as hora_hora FROM Actividad a WHERE a.id = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql1, new Object[] { actividadId });
        if (rows.isEmpty()) {
            throw new SchiNotesException("La actividad con id '" + actividadId + "' no existe.");
        }
        Actividad actividad = new Actividad();
        for (Map<String, Object> row : rows) {
            actividad.setId((int) row.get("id"));
            actividad.setNombre((String) row.get("nombre"));
            actividad.setDescripcion((String) row.get("descripcion"));
            actividad.setFecha((String) row.get("fecha_creacion"));
            actividad.setHora_ini((String) row.get("hora_hora"));
            actividad.setHora_fin((String) row.get("hora_fin"));
        }
        return actividad;
    }

}
