package edu.eci.arsw.SchiNotes.services;

import java.util.List;

import org.springframework.stereotype.Component;

import edu.eci.arsw.SchiNotes.exceptions.SchiNotesException;
import edu.eci.arsw.SchiNotes.model.Actividad;
import edu.eci.arsw.SchiNotes.model.Cuenta;
import edu.eci.arsw.SchiNotes.model.Horario;
import edu.eci.arsw.SchiNotes.model.Usuario;

/**
 * La clase SchiNotesService representa los servicios que se pueden ofrecer.
 */
@Component
public interface SchiNotesService {

    void registrarUsuario(Usuario usuario) throws SchiNotesException;

    void modificarUsuario(Usuario usuario) throws SchiNotesException;

    Usuario consultarUsuario(Usuario usuario) throws SchiNotesException;

    void crearCuenta(Cuenta cuenta) throws SchiNotesException;

    void modificarCuenta(Cuenta cuenta) throws SchiNotesException;

    void crearHorario(Horario horario) throws SchiNotesException;

    void modificarHorario(Horario horario) throws SchiNotesException;

    Horario consultarHorario() throws SchiNotesException;

    void eliminarHorario(String nombre) throws SchiNotesException;

    List<Horario> consultarHorarios() throws SchiNotesException;

    void agregarActividad(Actividad actividad) throws SchiNotesException;

    void modificarActividad(Actividad actividad) throws SchiNotesException;
    
}