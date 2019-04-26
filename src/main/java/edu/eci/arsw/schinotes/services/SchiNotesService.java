package edu.eci.arsw.schinotes.services;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.model.Actividad;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Grupo;
import edu.eci.arsw.schinotes.model.Horario;

import java.util.List;

/**
 * La clase SchiNotesService representa los servicios que se pueden ofrecer.
 */
public interface SchiNotesService {

    public List<Usuario> consultarUsuarios() throws SchiNotesException;

    public Usuario consultarUsuarioPorCorreo(String correo) throws SchiNotesException;

    public void registrarUsuario(Usuario usuario) throws SchiNotesException;

    public void crearCuenta(Cuenta cuenta) throws SchiNotesException;

    public Cuenta consultarCuentaPorCorreo(String correo) throws SchiNotesException;

    public void crearHorario(Horario horario) throws SchiNotesException;

    public Horario consultarHorarioByName(String correo, String nombre) throws SchiNotesException;

    public Horario consultarHorarioById(String correo, int id) throws SchiNotesException;

    public void agregarActividad(Actividad actividad) throws SchiNotesException;
    
    public Actividad consultarActividad(String correo, String nombre, String actividad) throws SchiNotesException;
    
    public List<Actividad> consultarActividades(String correo, String nombre) throws SchiNotesException;

    public void agregarAmigo(String correo1,String correo2) throws SchiNotesException;

    public List<Usuario> consultarAmigos(String correo) throws SchiNotesException;

    public List<Horario> consultarHorarios(String correo) throws SchiNotesException;
    
    public List<Usuario> consultarPersonasIncompleta(String correoPersona) throws SchiNotesException;

    public void crearGrupo(String correo, Grupo grupo) throws SchiNotesException;

	public List<Grupo> consultarGruposDeUnUsuario(String correo) throws SchiNotesException;

	public Actividad consultarActividadById(int actividadId) throws SchiNotesException;

    public void verificarCuenta(String correo) throws SchiNotesException;
    
    public boolean cuentaEstaVerificada(String correo);

    public void agregarNuevoIntegrante(int idGrupo, Usuario integrante, Horario horario) throws SchiNotesException;
    
    public Horario consultarHorarioPorGrupo(int idGrupo) throws SchiNotesException;

    public List<Actividad> consultarActividadesPorHorarioId(int Horario) throws SchiNotesException;
    
}
