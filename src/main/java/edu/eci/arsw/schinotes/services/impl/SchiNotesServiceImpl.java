package edu.eci.arsw.schinotes.services.impl;

import edu.eci.arsw.schinotes.dao.ActividadDAO;
import edu.eci.arsw.schinotes.dao.CuentaDAO;
import edu.eci.arsw.schinotes.dao.DiasPorHorarioDAO;
import edu.eci.arsw.schinotes.dao.GrupoDAO;
import edu.eci.arsw.schinotes.dao.UsuarioDAO;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Actividad;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.DiaDeLaSemana;
import edu.eci.arsw.schinotes.model.Grupo;
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

    @Autowired
    private GrupoDAO grupoDAO;

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
        int id = horarioDAO.getMaxId();
        horario.setId(id + 1);
        horario.setDiasDeLaSemana(horario.calcularDias(horario.getNumeroDias()));
        horario.setHoras(horario.calcularHoras(horario.getIntervaloHoras()));
        horarioDAO.saveHorario(horario);
        DiasPorHorarioDAO.saveDiasPorHorario(horario);
        for (DiaDeLaSemana diaSemana : horario.getDiasDeLaSemana()) {
            System.out.println(horario.getDiasDeLaSemana().size());
            for (Hora h : horario.getHoras()) {
                horaDAO.saveHora(h, horario.getId(), diaSemana.getNombre());
            }
        }
    }

    @Override
    public Horario consultarHorarioByName(String correo, String nombre) throws SchiNotesException {
        Horario horario = horarioDAO.getHorarioByName(correo, nombre);
        horario.setDiasDeLaSemana(DiasPorHorarioDAO.getDiasByName(nombre));
        horario.setHoras(horaDAO.getHorasByName(nombre));
        return horario;
    }

    @Override
    public Horario consultarHorarioById(String correo, int id) throws SchiNotesException {
        Horario horario = horarioDAO.getHorarioById(correo, id);
        horario.setDiasDeLaSemana(DiasPorHorarioDAO.getDiasById(id));
        horario.setHoras(horaDAO.getHorasById(id));
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

    @Override
    public List<Horario> consultarHorarios(String correo) throws SchiNotesException {
        return horarioDAO.getHorarios(correo);
    }

    @Override
    public List<Usuario> consultarPersonasIncompleta(String correoPersona) throws SchiNotesException {
        return usuarioDAO.loadUsuarioIncomplete(correoPersona);
    }

    @Override
    public void crearGrupo(String correo, Grupo grupo) throws SchiNotesException {
        Usuario usuario = consultarUsuarioPorCorreo(correo);
        Horario horario = new Horario(1, "Horario-"+grupo.getNombre(), 100, 7);
        crearHorario(horario);
        Horario horarioGrupo = consultarHorarioByName(null, "Horario-"+grupo.getNombre());
        Grupo g = grupo;
        g.setHorarioNombre(horarioGrupo);
        grupoDAO.saveGrupo(usuario.getIdentificacion(), g);
    }

    @Override
    public List<Grupo> consultarGruposDeUnUsuario(String correo) throws SchiNotesException {
        return grupoDAO.loadGroupsByUser(correo);
    }

    @Override
    public Actividad consultarActividadById(int actividadId) throws SchiNotesException {
        return actividadDAO.loadActividadById(actividadId);
    }

    @Override
    public void verificarCuenta(String correo) throws SchiNotesException {
        cuentaDAO.updateVerificadaCuentaByCorreo(correo);
    }

    @Override
    public boolean cuentaEstaVerificada(String correo) {
        return cuentaDAO.loadBoolCuentaVerificada(correo);
    }

}
