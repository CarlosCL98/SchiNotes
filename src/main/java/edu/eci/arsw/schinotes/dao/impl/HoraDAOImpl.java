package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.dao.CuentaDAO;
import edu.eci.arsw.schinotes.dao.HoraDao;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Hora;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author carloscl
 */
@Repository
public class HoraDAOImpl implements HoraDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
   
    
    @Override
    public void saveHora(Hora hora,String horario,String dia) throws SchiNotesException {
        System.out.println(hora.getHora().toString());
        
        String query = "INSERT INTO hora(hora,dias_por_horario_horario_nombre,dias_por_horario_dia_nombre) VALUES(TO_TIMESTAMP(?,'HH24:MI:SS'),?,?)";
        jdbcTemplate.update(query, new Object[]{
            hora.getHora(),horario,dia
        });
        
    }

}
