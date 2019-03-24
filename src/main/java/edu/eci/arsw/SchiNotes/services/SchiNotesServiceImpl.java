package edu.eci.arsw.SchiNotes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.SchiNotes.exceptions.SchiNotesException;
import edu.eci.arsw.SchiNotes.model.Actividad;
import edu.eci.arsw.SchiNotes.model.Cuenta;
import edu.eci.arsw.SchiNotes.model.Horario;
import edu.eci.arsw.SchiNotes.model.Usuario;
import edu.eci.arsw.SchiNotes.persistence.ActividadRepository;
import edu.eci.arsw.SchiNotes.persistence.CuentaRepository;
import edu.eci.arsw.SchiNotes.persistence.GrupoRepository;
import edu.eci.arsw.SchiNotes.persistence.HorarioRepository;
import edu.eci.arsw.SchiNotes.persistence.NotaRepository;
import edu.eci.arsw.SchiNotes.persistence.UsuarioRepository;

@Service
/**
 * HorarioServiceImpl
 */
public class SchiNotesServiceImpl implements SchiNotesService {

    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;



    @Override
    public void registrarUsuario(Usuario usuario)  {
        
        usuarioRepository.save(usuario);
    }

    @Override
    public void modificarUsuario(Usuario usuario) throws SchiNotesException {

    }

    @Override
    public Usuario consultarUsuario(int identificacion) throws SchiNotesException {
        return null;
    }

    @Override
    public void crearCuenta(Cuenta cuenta) throws SchiNotesException {

    }

    @Override
    public void modificarCuenta(Cuenta cuenta) throws SchiNotesException {

    }

    @Override
    public void crearHorario(Horario horario) throws SchiNotesException {

    }

    @Override
    public void modificarHorario(Horario horario) throws SchiNotesException {

    }

    @Override
    public Horario consultarHorario() throws SchiNotesException {
        return null;
    }

    @Override
    public void eliminarHorario(String nombre) throws SchiNotesException {

    }

    @Override
    public List<Horario> consultarHorarios() throws SchiNotesException {
        return null;
    }

    @Override
    public void agregarActividad(Actividad actividad) throws SchiNotesException {

    }

    @Override
    public void modificarActividad(Actividad actividad) throws SchiNotesException {

    }
    
    
}