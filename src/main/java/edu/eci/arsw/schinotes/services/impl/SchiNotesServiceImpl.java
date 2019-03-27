package edu.eci.arsw.schinotes.services.impl;

import edu.eci.arsw.schinotes.dao.CuentaDAO;
import edu.eci.arsw.schinotes.dao.UsuarioDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.services.SchiNotesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * HorarioServiceImpl
 */
@Service
public class SchiNotesServiceImpl implements SchiNotesService {
    
    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Autowired
    private CuentaDAO cuentaDAO;
    
    
    @Override
    public List<Usuario> consultarUsuarios() throws SchiNotesException {
        return usuarioDAO.loadAll();
    }
    
    @Override
    public Usuario consultarUsuarioPorCorreo(String correo) throws SchiNotesException {
        return usuarioDAO.loadUsuarioByEmail(correo);
    }

    @Override
    public void registrarUsuario(Usuario usuario) throws SchiNotesException {
        usuarioDAO.saveUsuario(usuario);
    }

    @Override
    public void crearCuenta(Cuenta cuenta) throws SchiNotesException {
        cuentaDAO.saveCuenta(cuenta);
    }

    @Override
    public Cuenta consultarCuentaPorCorreo(String correo) throws SchiNotesException {
        return cuentaDAO.loadCuentaByEmail(correo);
    }
    
}