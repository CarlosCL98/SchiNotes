package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.dao.DiasPorHorarioDAO;
import edu.eci.arsw.schinotes.dao.HorarioDao;
import edu.eci.arsw.schinotes.dao.UsuarioDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.DiaDeLaSemana;
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

/**
 *
 * @author carloscl
 */
@Repository
public class DiasPorHorarioImpl implements DiasPorHorarioDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveDiasPorHorario(Horario horario) throws SchiNotesException {
        System.out.println(horario.getDiasDeLaSemana());
        for(DiaDeLaSemana ddls:horario.getDiasDeLaSemana()){
            String query = "INSERT INTO dias_Por_horario (horario_nombre,dia_nombre) VALUES(?,?)";
            jdbcTemplate.update(query, new Object[]{
            horario.getNombre(),ddls.getNombre()
        });
        }
    }

    @Override
    public List<DiaDeLaSemana> getDias(String nomString) throws SchiNotesException {
        String sql = "SELECT * FROM dias_Por_horario d JOIN horario h ON(h.nombre = d.horario_nombre) WHERE d.horario_nombre = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,new Object[]{
            nomString
        });

        List<DiaDeLaSemana> ddls = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            DiaDeLaSemana dia = new DiaDeLaSemana();
            dia.setNombre((String) row.get("dia_nombre"));
            ddls.add(dia);
        }
        if (ddls.isEmpty()) {
            throw new SchiNotesException("No hay usuarios existentes.");
        }
        return ddls;
    }

}
