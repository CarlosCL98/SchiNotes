package edu.eci.arsw.schinotes.services;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.model.Actividad;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Horario;

import java.util.List;

import org.springframework.stereotype.Component;

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

    public Horario consultarHorario(String correo, String nombre) throws SchiNotesException;

    public void agregarActividad(Actividad actividad) throws SchiNotesException;

    public void agregarAmigo(String correo1,String correo2) throws SchiNotesException;

    public List<Usuario> consultarAmigos(String correo) throws SchiNotesException;

    /*
    void modificarCuenta(Cuenta cuenta) throws SchiNotesException;

    void modificarHorario(Horario horario) throws SchiNotesException;

    void eliminarHorario(String nombre) throws SchiNotesException;

    List<Horario> consultarHorarios() throws SchiNotesException;

    void modificarActividad(Actividad actividad) throws SchiNotesException;
    
    void modificarUsuario(Usuario usuario) throws SchiNotesException;
     */
}
