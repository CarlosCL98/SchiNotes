package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Hora;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Override
    public List<Hora> getHorasByName(String nombreHorario) throws SchiNotesException {
        String query = "SELECT hora FROM horario h JOIN dias_por_horario dph ON (h.id=dph.horario_id) JOIN hora ho ON (ho.dias_por_horario_horario_id=dph.horario_id) WHERE h.nombre = ? GROUP BY hora ORDER BY hora;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[] { nombreHorario });
        List<Hora> ddls = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Hora dia = new Hora();
            dia.setHora(row.get("hora").toString());
            ddls.add(dia);
        }
        if (ddls.isEmpty()) {
            throw new SchiNotesException("No hay horas existentes.");
        }
        return ddls;
    }

    @Override
    public List<Hora> getHorasById(int idHorario) throws SchiNotesException {
        String query = "SELECT hora FROM hora WHERE dias_por_horario_horario_id = ? GROUP BY hora ORDER BY hora;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[] { idHorario });
        List<Hora> ddls = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Hora dia = new Hora();
            dia.setHora(row.get("hora").toString());
            ddls.add(dia);
        }
        if (ddls.isEmpty()) {
            throw new SchiNotesException("No hay horas existentes.");
        }
        return ddls;
    }

    @Override
    public void saveHora(Hora hora, int idHorario, String dia) throws SchiNotesException {
        String query = "INSERT INTO hora (hora,dias_por_horario_horario_id,dias_por_horario_dia_nombre) VALUES (TO_TIMESTAMP(?,'HH24:MI'),?,?)";
        jdbcTemplate.update(query, new Object[] { hora.getHora(), idHorario, dia });

    }

}
