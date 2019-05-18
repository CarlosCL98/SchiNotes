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
import edu.eci.arsw.schinotes.model.Notificacion;
import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.services.SchiNotesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import edu.eci.arsw.schinotes.dao.HoraDAO;
import edu.eci.arsw.schinotes.dao.HorarioDAO;

/**
 * HorarioServiceImpl
 */
@Service
public class SchiNotesServiceImpl implements SchiNotesService {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        //Cifrar la contraseña
        cuenta.setContrasena(passwordEncoder.encode(cuenta.getContrasena()));
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
        return actividadDAO.loadActividadByCorreo(correo, nombre, actividad);
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
        Horario horario = new Horario(1, "Horario-" + grupo.getNombre(), 100, 7);
        crearHorario(horario);
        Horario horarioGrupo = consultarHorarioByName(null, "Horario-" + grupo.getNombre());
        Grupo g = grupo;
        g.setHorarioNombre(horarioGrupo);
        grupoDAO.saveGrupo(usuario.getIdentificacion(), g);
    }

    @Override
    public List<Grupo> consultarGruposDeUnUsuario(String correo) throws SchiNotesException {
        return grupoDAO.loadGroupsByUser(correo);
    }

    @Override
    public void agregarNuevoIntegrante(int idGrupo, Usuario integrante, Horario horario) throws SchiNotesException {
        Horario horarioGrupo = grupoDAO.loadHorarioGrupo(idGrupo);
        Horario horarioIntegrante = horarioDAO.getHorarioByName(integrante.getCuentaCorreo().getCorreo(),
                horario.getNombre());
        horarioIntegrante.setActividades(
                this.consultarActividades(integrante.getCuentaCorreo().getCorreo(), horario.getNombre()));
        integrarActividadesGrupo(horarioGrupo, horarioIntegrante);
        grupoDAO.saveIntegrante(idGrupo, integrante, horario);
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

    @Override
    public List<Actividad> consultarActividadesPorHorarioId(int idHorario) throws SchiNotesException {
        return actividadDAO.loadActividadesByHorarioId(idHorario);
    }

    @Override
    public Horario consultarHorarioPorGrupo(int idGrupo) throws SchiNotesException {
        Horario horario = horarioDAO.getHorarioGrupo(idGrupo);
        horario.setDiasDeLaSemana(DiasPorHorarioDAO.getDiasById(horario.getId()));
        horario.setHoras(horaDAO.getHorasById(horario.getId()));
        horario.setActividades(this.consultarActividadesPorHorarioId(horario.getId()));
        return horario;
    }

    private void integrarActividadesGrupo(Horario horarioGrupo, Horario horarioIntegrante) throws SchiNotesException {
        for (Actividad a : horarioIntegrante.getActividades()) {
            String hora_ini = a.getHora_ini().substring(0, 2);
            hora_ini += ":00:00";
            a.setHora_ini(hora_ini);
            int hora = Integer.parseInt(a.getHora_fin().substring(0, 2));
            hora += 1;
            String hora_fin;
            if (hora < 10) {
                hora_fin = "0";
                hora_fin += String.valueOf(hora);
            } else {
                hora_fin = String.valueOf(hora);
            }
            hora_fin += ":00:00";
            a.setHora_fin(hora_fin);
            a.setHorario_id(horarioGrupo.getId());
            this.agregarActividad(a);
        }
    }

    @Override
    public List<Grupo> consultarTodosLosGrupos() throws SchiNotesException {
        return grupoDAO.getAllGroups();
    }

    @Override
    public void eliminarIntegranteDeGrupo(int grupoId, String correo) throws SchiNotesException {
        grupoDAO.deleteUserFromGroup(grupoId, correo);
    }

    @Override
    public List<Actividad> consultarActividadesPorGrupo(int grupoId, String nombreHorario) throws SchiNotesException {
        return actividadDAO.loadActividadesByGroup(grupoId, nombreHorario);
    }

    @Override
    public void agregarNotificacion(String correoUsuario, Notificacion notificacion) throws SchiNotesException {
        Usuario usuario = this.consultarUsuarioPorCorreo(correoUsuario);
        notificacion.setUsuario(usuario);
        usuarioDAO.saveNotificaciones(notificacion);
    }

    @Override
    public List<Notificacion> consultarNotificaciones(String correo) throws SchiNotesException {
        return usuarioDAO.loadNotificaciones(correo);
    }

    @Override
    public void eliminarActividad(int idHorario, int idActividad) throws SchiNotesException {
        actividadDAO.eliminarActividad(idHorario,idActividad);
    }

    @Override
    public void eliminarAmigo(String correo, int idAmigo) throws SchiNotesException {
        Usuario usuario = this.consultarUsuarioPorCorreo(correo);
        usuarioDAO.deleteAmigo(usuario.getIdentificacion(), idAmigo);

    }

}
