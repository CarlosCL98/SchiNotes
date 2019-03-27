package edu.eci.arsw.schinotes.services;

import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * La clase SchiNotesService representa los servicios que se pueden ofrecer.
 */
@Component
public interface SchiNotesService {
    
    public List<Usuario> consultarUsuarios() throws SchiNotesException;

    public Usuario consultarUsuarioPorCorreo(String correo) throws SchiNotesException;

    public void registrarUsuario(Usuario usuario) throws SchiNotesException;
    /*
    void modificarUsuario(Usuario usuario) throws SchiNotesException;
    */
    public void crearCuenta(Cuenta cuenta) throws SchiNotesException;
    
    public Cuenta consultarCuentaPorCorreo(String correo) throws SchiNotesException;
    /*
    void modificarCuenta(Cuenta cuenta) throws SchiNotesException;

    void crearHorario(Horario horario) throws SchiNotesException;

    void modificarHorario(Horario horario) throws SchiNotesException;

    Horario consultarHorario() throws SchiNotesException;

    void eliminarHorario(String nombre) throws SchiNotesException;

    List<Horario> consultarHorarios() throws SchiNotesException;

    void agregarActividad(Actividad actividad) throws SchiNotesException;

    void modificarActividad(Actividad actividad) throws SchiNotesException;
    */
    
}