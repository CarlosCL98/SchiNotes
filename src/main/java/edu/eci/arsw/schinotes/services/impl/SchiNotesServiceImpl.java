package edu.eci.arsw.schinotes.services.impl;

import edu.eci.arsw.schinotes.dao.CuentaDAO;
import edu.eci.arsw.schinotes.dao.DiasPorHorarioDAO;
import edu.eci.arsw.schinotes.dao.HoraDao;
import edu.eci.arsw.schinotes.dao.HorarioDao;
import edu.eci.arsw.schinotes.dao.UsuarioDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.DiaDeLaSemana;
import edu.eci.arsw.schinotes.model.Hora;
import edu.eci.arsw.schinotes.model.Horario;
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
    
    @Autowired
    private HorarioDao horarioDAO;
    
    @Autowired
    private HoraDao horaDAO;

    @Autowired
    private DiasPorHorarioDAO DiasPorHorarioDAO;

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

    @Override
    public void crearHorario(Horario horario) throws SchiNotesException{

       
        horario.setDiasDeLaSemana(horario.calcularDias(5));
        horario.setHoras(horario.calcularHoras(120));

        horarioDAO.saveHorario(horario);
        
        DiasPorHorarioDAO.saveDiasPorHorario(horario);
       
        for (DiaDeLaSemana diaSemana : horario.getDiasDeLaSemana()) {
            System.out.println(horario.getDiasDeLaSemana().size());
            for(Hora h:horario.getHoras()){
                horaDAO.saveHora(h,horario.getNombre(),diaSemana.getNombre());
            }
        } 
    }

    @Override
    public Horario consultarHorario(String correo,String nombre) throws SchiNotesException {
        Horario horario = horarioDAO.getHorario(correo,nombre);
        horario.setDiasDeLaSemana(DiasPorHorarioDAO.getDias(nombre));        
        return horario;
        
        
    }
    
}