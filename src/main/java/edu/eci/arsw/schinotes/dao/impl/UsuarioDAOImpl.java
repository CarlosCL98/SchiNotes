package edu.eci.arsw.schinotes.dao.impl;

import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.dao.UsuarioDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Notificacion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
public class UsuarioDAOImpl implements UsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Usuario> loadAll() throws SchiNotesException {
        String sql = "SELECT * FROM usuario u JOIN cuenta cu ON(u.cuenta_correo = cu.correo)";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Usuario> usuarios = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Usuario usuario = new Usuario();
            int identificacion = (int) row.get("identificacion");
            usuario.setIdentificacion(identificacion);
            usuario.setNombre((String) row.get("nombre"));
            usuario.setApellido((String) row.get("apellido"));
            usuario.setFoto((Byte[]) row.get("foto"));
            usuario.setIntereses((String) row.get("intereses"));
            Cuenta cuenta = new Cuenta();
            cuenta.setCorreo((String) row.get("correo"));
            cuenta.setContrasena((String) row.get("contrasena"));
            cuenta.setNickname((String) row.get("nickname"));
            usuario.setCuentaCorreo(cuenta);
            usuario.setMisAmigos(loadMisAmigos(identificacion));
            usuarios.add(usuario);
        }
        if (usuarios.isEmpty()) {
            throw new SchiNotesException("No hay usuarios existentes.");
        }
        return usuarios;
    }

    @Override
    public Usuario loadUsuarioByEmail(String correo) throws SchiNotesException {
        String sql = "SELECT * FROM usuario u JOIN cuenta cu ON(u.cuenta_correo = cu.correo) WHERE u.cuenta_correo = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { correo });
        if (rows.isEmpty()) {
            throw new SchiNotesException("El usuario con correo '" + correo + "' no existe.");
        }

        Usuario usuario = (Usuario) jdbcTemplate.queryForObject(sql, new Object[] { correo }, new RowMapper<Usuario>() {
            @Override
            public Usuario mapRow(ResultSet rs, int rwNumber) throws SQLException {
                Usuario usuario = new Usuario();
                int identificacion = rs.getInt("identificacion");
                usuario.setIdentificacion(identificacion);
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setFoto((Byte[]) rs.getObject("foto"));
                usuario.setIntereses(rs.getString("intereses"));
                Cuenta cuenta = new Cuenta();
                cuenta.setCorreo(rs.getString("correo"));
                cuenta.setContrasena(rs.getString("contrasena"));
                cuenta.setNickname(rs.getString("nickname"));
                usuario.setMisAmigos(loadMisAmigos(identificacion));
                usuario.setCuentaCorreo(cuenta);
                return usuario;
            }
        });
        return usuario;
    }

    @Override
    public void saveUsuario(Usuario usuario) throws SchiNotesException {
        String correo = usuario.getCuentaCorreo().getCorreo();
        Usuario u1 = null;
        try {
            u1 = loadUsuarioByEmail(correo);
        } catch (SchiNotesException ex) {
            if (ex.getMessage().equals("El usuario con correo '" + correo + "' no existe.")) {
                if (u1 != null) {
                    throw new SchiNotesException("El usuario con correo '" + correo + "' ya existe.");
                }
            }
        }
        String sql1 = "SELECT CASE WHEN MAX(u.identificacion) is NULL THEN 0 ELSE MAX(u.identificacion) END FROM usuario u";
        int id = jdbcTemplate.queryForObject(sql1, Integer.class);
        String sql2 = "INSERT INTO usuario (identificacion,nombre,apellido,foto,intereses,cuenta_correo) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql2, new Object[] { id + 1, usuario.getNombre(), usuario.getApellido(), usuario.getFoto(),
                usuario.getIntereses(), usuario.getCuentaCorreo().getCorreo() });
    }

    @Override
    public void saveAmigos(int id1, int id2) throws SchiNotesException {
        String sql = "INSERT INTO amigo(usuario_identificacion,usuario_2_identificacion,fecha) VALUES(?,?,?)";
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        jdbcTemplate.update(sql, new Object[] { id1, id2, date });

    }

    @Override
    public List<Usuario> getAmigos(int identificacion) throws SchiNotesException {
        String sql = "select * from amigo,usuario where amigo.usuario_2_identificacion = usuario.identificacion and usuario_identificacion = ?;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { identificacion });
        List<Usuario> usuarios = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Usuario usuario = new Usuario();
            usuario.setIdentificacion((int) row.get("identificacion"));
            usuario.setNombre((String) row.get("nombre"));
            usuario.setApellido((String) row.get("apellido"));
            usuario.setFoto((Byte[]) row.get("foto"));
            usuario.setIntereses((String) row.get("intereses"));
            Cuenta cuenta = new Cuenta();
            cuenta.setCorreo((String) row.get("cuenta_correo"));
            cuenta.setContrasena((String) row.get("contrasena"));
            cuenta.setNickname((String) row.get("cuenta_correo"));
            usuario.setCuentaCorreo(cuenta);
            usuarios.add(usuario);
        }
        if (usuarios.isEmpty()) {
            throw new SchiNotesException("No hay usuarios existentes.");
        }
        return usuarios;
    }

    @Override
    public List<Usuario> deleteAmigos(String correo) throws SchiNotesException {
        return null;
    }

    @Override
    public List<Usuario> loadUsuarioIncomplete(String correoPersonas) throws SchiNotesException {
        String sql = "SELECT * FROM usuario u JOIN cuenta cu ON(u.cuenta_correo = cu.correo) WHERE u.cuenta_correo LIKE('%"
                + correoPersonas + "%');";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Usuario> usuarios = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Usuario usuario = new Usuario();
            usuario.setIdentificacion((int) row.get("identificacion"));
            usuario.setNombre((String) row.get("nombre"));
            usuario.setApellido((String) row.get("apellido"));
            usuario.setFoto((Byte[]) row.get("foto"));
            usuario.setIntereses((String) row.get("intereses"));
            Cuenta cuenta = new Cuenta();
            cuenta.setCorreo((String) row.get("cuenta_correo"));
            cuenta.setContrasena((String) row.get("contrasena"));
            cuenta.setNickname((String) row.get("cuenta_correo"));
            usuario.setCuentaCorreo(cuenta);
            usuarios.add(usuario);
        }
        return usuarios;
    }

    private List<Usuario> loadMisAmigos(int identificacion) {
        String sql = "SELECT * FROM usuario u, cuenta cu, amigo a WHERE u.cuenta_correo = cu.correo AND u.identificacion = a.usuario_2_identificacion AND a.usuario_identificacion = ?;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { identificacion });
        List<Usuario> usuarios = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Usuario usuario = new Usuario();
            usuario.setIdentificacion((int) row.get("identificacion"));
            usuario.setNombre((String) row.get("nombre"));
            usuario.setApellido((String) row.get("apellido"));
            usuario.setFoto((Byte[]) row.get("foto"));
            usuario.setIntereses((String) row.get("intereses"));
            Cuenta cuenta = new Cuenta();
            cuenta.setCorreo((String) row.get("correo"));
            cuenta.setContrasena((String) row.get("contrasena"));
            cuenta.setNickname((String) row.get("nickname"));
            usuario.setCuentaCorreo(cuenta);
            usuarios.add(usuario);
        }
        return usuarios;
    }

    @Override
    public void saveNotificaciones(Notificacion notificacion) throws SchiNotesException {       

        String sql1 = "SELECT CASE WHEN MAX(n.id) is NULL THEN 0 ELSE MAX(n.id) END FROM notificacion n";
        int id = jdbcTemplate.queryForObject(sql1, Integer.class);
        String sql2 = "INSERT INTO notificacion (id,descripcion,usuario_identificacion) VALUES (?,?,?)";
        jdbcTemplate.update(sql2, new Object[] { id + 1, notificacion.getDescripcion(),notificacion.getUsuario().getIdentificacion()});
    }

    @Override
    public List<Notificacion> loadNotificaciones(String correo) throws SchiNotesException {
        String sql = "SELECT * FROM notificacion n,cuenta cu,usuario u WHERE cu.correo = ? AND u.cuenta_correo = cu.correo AND u.identificacion = n.usuario_identificacion";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { correo });
        List<Notificacion> notificaciones = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Notificacion notificacion = new Notificacion();
            int id = (int) row.get("id");
            notificacion.setId(id);
            notificacion.setDescripcion((String) row.get("descripcion"));
            Usuario usuario = new Usuario();
            int identificacion = (int) row.get("identificacion");
            usuario.setIdentificacion(identificacion);
            usuario.setNombre((String) row.get("nombre"));
            usuario.setApellido((String) row.get("apellido"));
            usuario.setFoto((Byte[]) row.get("foto"));
            usuario.setIntereses((String) row.get("intereses"));
            Cuenta cuenta = new Cuenta();
            cuenta.setCorreo((String) row.get("correo"));
            cuenta.setContrasena((String) row.get("contrasena"));
            cuenta.setNickname((String) row.get("nickname"));
            usuario.setCuentaCorreo(cuenta);
            usuario.setMisAmigos(loadMisAmigos(identificacion));
            notificacion.setUsuario(usuario);
            notificaciones.add(notificacion);
        }
        
        return notificaciones;
    }
    

}
