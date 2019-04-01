package edu.eci.arsw.schinotes.services.impl;

import edu.eci.arsw.schinotes.dao.ActividadDAO;
import edu.eci.arsw.schinotes.dao.CuentaDAO;
import edu.eci.arsw.schinotes.dao.DiasPorHorarioDAO;
import edu.eci.arsw.schinotes.dao.UsuarioDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Actividad;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.DiaDeLaSemana;
import edu.eci.arsw.schinotes.model.Hora;
import edu.eci.arsw.schinotes.model.Horario;
import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.services.SchiNotesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.arsw.schinotes.dao.HoraDAO;
import edu.eci.arsw.schinotes.dao.HorarioDAO;

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
    private HorarioDAO horarioDAO;

    @Autowired
    private HoraDAO horaDAO;

    @Autowired
    private DiasPorHorarioDAO DiasPorHorarioDAO;
    
    @Autowired
    private ActividadDAO actividadDAO;

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
    public void crearHorario(Horario horario) throws SchiNotesException {
        horario.setDiasDeLaSemana(horario.calcularDias(5));
        horario.setHoras(horario.calcularHoras(120));
        horarioDAO.saveHorario(horario);
        DiasPorHorarioDAO.saveDiasPorHorario(horario);
        for (DiaDeLaSemana diaSemana : horario.getDiasDeLaSemana()) {
            System.out.println(horario.getDiasDeLaSemana().size());
            for (Hora h : horario.getHoras()) {
                horaDAO.saveHora(h, horario.getNombre(), diaSemana.getNombre());
            }
        }
    }

    @Override
    public Horario consultarHorario(String correo, String nombre) throws SchiNotesException {
        Horario horario = horarioDAO.getHorario(correo, nombre);
        horario.setDiasDeLaSemana(DiasPorHorarioDAO.getDias(nombre));
        horario.setHoras(horaDAO.getHoras(nombre));
        
        return horario;
    }

    @Override
    public void agregarActividad(Actividad actividad) throws SchiNotesException {
        actividadDAO.saveActividad(actividad);
    }
    
    @Override
    public Actividad consultarActividad(String correo, String nombre, String actividad) throws SchiNotesException {
        return actividadDAO.loadActividad(correo, nombre, actividad);
    }
    
    @Override
    public List<Actividad> consultarActividades(String correo, String nombre) throws SchiNotesException {
        return actividadDAO.loadAll(correo, nombre);
    }

    @Override
    public void agregarAmigo(String correo1, String correo2) throws SchiNotesException {
        Usuario usuario1 = usuarioDAO.loadUsuarioByEmail(correo1);
        Usuario usuario2 = usuarioDAO.loadUsuarioByEmail(correo2);
        usuarioDAO.saveAmigos(usuario1.getIdentificacion(), usuario2.getIdentificacion());
    }

    @Override
    public List<Usuario> consultarAmigos(String correo) throws SchiNotesException {
        
        return usuarioDAO.getAmigos(usuarioDAO.loadUsuarioByEmail(correo).getIdentificacion());
    }

}
