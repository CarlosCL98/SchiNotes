package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.dao.CuentaDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.DiaDeLaSemana;
import edu.eci.arsw.schinotes.model.Hora;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import edu.eci.arsw.schinotes.dao.HoraDAO;

/**
 *
 * @author carloscl
 */
@Repository
public class HoraDAOImpl implements HoraDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

   
    
    
    /*@Override
    public void saveHora(Hora hora, String horario, String dia) throws SchiNotesException {
        System.out.println(hora.getHora().toString());

        String query = "INSERT INTO hora(hora,dias_por_horario_horario_nombre,dias_por_horario_dia_nombre) VALUES(TO_TIMESTAMP(?,'HH24:MI:SS'),?,?)";
        jdbcTemplate.update(query, new Object[] { hora.getHora(), horario, dia });

    }*/
    
    
 
    @Override
    public List<Hora> getHoras(String nombreHorario) throws SchiNotesException {
        System.out.println("holaaa");
        String query = "SELECT hora FROM hora,dias_por_horario WHERE dias_por_horario_horario_nombre = ? GROUP BY hora ORDER BY hora;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[] {
            nombreHorario
        });

        List<Hora> ddls = new ArrayList<>();
        System.out.println(rows.size());
        for (Map<String, Object> row : rows) {
            Hora dia = new Hora();
            dia.setHora(row.get("hora").toString());
            ddls.add(dia);  
        }
        if (ddls.isEmpty()) {
            throw new SchiNotesException("No hay usuarios existentes.");
        } 
        return ddls;
    }

    @Override
    public void saveHora(Hora hora, String horario, String dia) throws SchiNotesException {
        String query = "INSERT INTO hora(hora,dias_por_horario_horario_nombre,dias_por_horario_dia_nombre) VALUES(TO_TIMESTAMP(?,'HH24:MI:SS'),?,?)";
        jdbcTemplate.update(query, new Object[] { hora.getHora(), horario, dia });

    }

}
