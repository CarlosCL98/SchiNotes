package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.dao.ActividadDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Actividad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String sql2 = "INSERT INTO Actividad (id,nombre,descripcion,fecha,hora_hora,hora_dias_por_horario_horario_nombre,Hora_Dias_Por_Horario_dia_nombre) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql2, new Object[]{
            id+1, actividad.getNombre(), actividad.getDescripcion(), actividad.getFecha(), actividad.getHora(), actividad.getDia(), actividad.getHorario()
        });
    }

}
