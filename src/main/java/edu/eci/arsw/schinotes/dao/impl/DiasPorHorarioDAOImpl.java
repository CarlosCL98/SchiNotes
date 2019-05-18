package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.dao.DiasPorHorarioDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.DiaDeLaSemana;
import edu.eci.arsw.schinotes.model.Horario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author carloscl
 */
@Repository
public class DiasPorHorarioDAOImpl implements DiasPorHorarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveDiasPorHorario(Horario horario) throws SchiNotesException {
        for (DiaDeLaSemana ddls : horario.getDiasDeLaSemana()) {
            String query = "INSERT INTO dias_Por_horario (horario_id,dia_nombre) VALUES(?,?)";
            jdbcTemplate.update(query, new Object[] { horario.getId(), ddls.getNombre() });
        }
    }

    @Override
    public List<DiaDeLaSemana> getDiasByName(String nomString) throws SchiNotesException {
        String sql = "SELECT * FROM dias_Por_horario d JOIN horario h ON(h.id = d.horario_id) WHERE h.nombre = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { nomString });
        List<DiaDeLaSemana> ddls = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            DiaDeLaSemana dia = new DiaDeLaSemana();
            dia.setNombre((String) row.get("dia_nombre"));
            ddls.add(dia);
        }
        if (ddls.isEmpty()) {
            throw new SchiNotesException("No hay días existentes.");
        }
        return ddls;
    }

    @Override
    public List<DiaDeLaSemana> getDiasById(int id) throws SchiNotesException {
        String sql = "SELECT * FROM dias_Por_horario d JOIN horario h ON(h.id = d.horario_id) WHERE d.horario_id = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { id });
        List<DiaDeLaSemana> ddls = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            DiaDeLaSemana dia = new DiaDeLaSemana();
            dia.setNombre((String) row.get("dia_nombre"));
            ddls.add(dia);
        }
        if (ddls.isEmpty()) {
            throw new SchiNotesException("No hay días existentes.");
        }
        return ddls;
    }

}
