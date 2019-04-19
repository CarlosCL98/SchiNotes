package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.dao.CuentaDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;

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
public class CuentaDAOImpl implements CuentaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Cuenta loadCuentaByEmail(String correo) throws SchiNotesException {
        String sql = "SELECT * FROM cuenta cu WHERE cu.correo = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { correo });
        if (rows.isEmpty()) {
            throw new SchiNotesException("La cuenta con correo '" + correo + "' no existe.");
        }
        return (Cuenta) jdbcTemplate.queryForObject(sql, new Object[] { correo }, new RowMapper<Cuenta>() {
            @Override
            public Cuenta mapRow(ResultSet rs, int rwNumber) throws SQLException {
                Cuenta cuenta = new Cuenta();
                cuenta.setCorreo(rs.getString("correo"));
                cuenta.setContrasena(rs.getString("contrasena"));
                cuenta.setNickname(rs.getString("nickname"));
                return cuenta;
            }
        });
    }

    @Override
    public void saveCuenta(Cuenta cuenta) throws SchiNotesException {
        String correo = cuenta.getCorreo();
        Cuenta c1 = null;
        try {
            c1 = loadCuentaByEmail(correo);
        } catch (SchiNotesException ex) {
            if (ex.getMessage().equals("La cuenta con correo '" + correo + "' no existe.")) {
                if (c1 != null) {
                    throw new SchiNotesException("La cuenta con correo '" + correo + "' ya existe.");
                }
            }
        }
        String sql = "INSERT INTO cuenta (correo,contrasena,nickname) VALUES (?,?,?)";
        jdbcTemplate.update(sql, new Object[] { cuenta.getCorreo(), cuenta.getContrasena(), cuenta.getNickname() });
    }

}
