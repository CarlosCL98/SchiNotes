package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.dao.ActividadDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Actividad;
import java.sql.Date;
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
        String sql2 = "INSERT INTO Actividad (id,nombre,descripcion,fecha,hora_hora,hora_dias_por_horario_horario_nombre,Hora_Dias_Por_Horario_dia_nombre) VALUES (?,?,?,TO_DATE(?,'YYYY-MM-DD'),TO_TIMESTAMP(?,'HH24:MI:SS'),?,?)";
        jdbcTemplate.update(sql2, new Object[]{
            id + 1, actividad.getNombre(), actividad.getDescripcion(), actividad.getFecha(), actividad.getHora(), actividad.getHorario(), actividad.getDia()
        });
        String sql3 = "INSERT INTO actividad_por_horario (actividad_id,horario_nombre) VALUES (?,?)";
        jdbcTemplate.update(sql3, new Object[]{
            id + 1, actividad.getHorario()
        });
    }

    @Override
    public Actividad loadActividad(String correo, String nombre, String actividad) throws SchiNotesException {
        String sql = "SELECT a.id,a.nombre,a.descripcion,a.fecha,to_char(a.hora_hora, 'HH12:MI:SS') as hora_hora,a.hora_dias_por_horario_dia_nombre,a.hora_dias_por_horario_horario_nombre\n"
                + "FROM usuario u JOIN horario h ON (u.identificacion=h.usuario_identificacion) \n"
                + "JOIN actividad_por_horario aph ON (h.nombre=aph.horario_nombre)\n"
                + "JOIN actividad a ON (a.id=aph.actividad_id)\n"
                + "WHERE u.cuenta_correo = ? AND h.nombre = ? AND a.nombre = ?;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{
            correo, nombre, actividad
        });
        if (rows.isEmpty()) {
            throw new SchiNotesException("La actividad '" + actividad + "' del usuario con correo '" + correo + "' y en el horario '" + nombre + "' no existe.");
        }
        return (Actividad) jdbcTemplate.queryForObject(sql, new Object[]{correo, nombre, actividad}, new RowMapper<Actividad>() {
            @Override
            public Actividad mapRow(ResultSet rs, int rwNumber) throws SQLException {
                Actividad actividad = new Actividad();
                actividad.setId(rs.getInt("id"));
                actividad.setNombre(rs.getString("nombre"));
                actividad.setDescripcion(rs.getString("descripcion"));
                actividad.setFecha((Date) rs.getObject("fecha"));
                actividad.setHorario(rs.getString("hora_dias_por_horario_horario_nombre"));
                actividad.setDia(rs.getString("hora_dias_por_horario_dia_nombre"));
                actividad.setHora(rs.getString("hora_hora"));
                return actividad;
            }
        });
    }

    @Override
    public List<Actividad> loadAll(String correo, String nombre) throws SchiNotesException {
        String sql = "SELECT a.id,a.nombre,a.descripcion,a.fecha,to_char(a.hora_hora, 'HH12:MI:SS') as hora_hora,a.hora_dias_por_horario_dia_nombre,a.hora_dias_por_horario_horario_nombre\n"
                + "FROM usuario u JOIN horario h ON (u.identificacion=h.usuario_identificacion) \n"
                + "JOIN actividad_por_horario aph ON (h.nombre=aph.horario_nombre)\n"
                + "JOIN actividad a ON (a.id=aph.actividad_id)\n"
                + "WHERE u.cuenta_correo = ? AND h.nombre = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{
            correo, nombre
        });
        List<Actividad> actividades = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Actividad actividad = new Actividad();
            actividad.setId((int) row.get("id"));
            actividad.setNombre((String) row.get("nombre"));
            actividad.setDescripcion((String) row.get("descripcion"));
            actividad.setFecha((Date) row.get("fecha"));
            actividad.setHorario((String) row.get("hora_dias_por_horario_horario_nombre"));
            actividad.setDia((String) row.get("hora_dias_por_horario_dia_nombre"));
            actividad.setHora((String) row.get("hora_hora"));
            actividades.add(actividad);
        }
        if (actividades.isEmpty()) {
            throw new SchiNotesException("El usuario con correo '" + correo + "' y en el horario '" + nombre + "' no tiene actividades.");
        }
        return actividades;
    }

}
