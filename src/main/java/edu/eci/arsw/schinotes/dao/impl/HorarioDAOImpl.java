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
    public void saveHorario(Horario horario) throws SchiNotesException {        
        String query = "INSERT INTO horario (nombre,usuario_identificacion) VALUES(?,?)";

        jdbcTemplate.update(query, new Object[]{
            horario.getNombre(),horario.getUsuario().getIdentificacion()
        });
    }

    @Override
    public Horario getHorario(String correo,String nombre) throws SchiNotesException {
        String query = "SELECT h.nombre as hnombre,h.usuario_identificacion,u.identificacion,u.nombre as unombre,u.apellido,u.cuenta_correo FROM horario h JOIN usuario u ON(h.usuario_identificacion = u.identificacion) WHERE h.nombre = ? and u.cuenta_correo = ?";

        return (Horario) jdbcTemplate.queryForObject(query, new Object[]{nombre,correo}, new RowMapper<Horario>() {
            @Override
            public Horario mapRow(ResultSet rs, int rwNumber) throws SQLException {
                Horario horario = new Horario();
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

}
