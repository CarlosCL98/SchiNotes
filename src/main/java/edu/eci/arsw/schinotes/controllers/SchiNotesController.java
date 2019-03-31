package edu.eci.arsw.schinotes.controllers;

import edu.eci.arsw.schinotes.exceptions.FoundException;
import edu.eci.arsw.schinotes.exceptions.NotFoundException;
import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Actividad;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Horario;
import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.services.SchiNotesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author cristian
 */
@RestController
@RequestMapping(value = "/schinotes")
public class SchiNotesController {

    @Autowired
    SchiNotesService schiNotesService;

    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> recursoConsultarUsuarios() throws NotFoundException {
        System.out.println("soy el controller");
        try {
            List<Usuario> usuarios = schiNotesService.consultarUsuarios();
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (SchiNotesException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/usuarios/{correo}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> recursoConsultarUsuarioPorCorreo(@PathVariable String correo) throws NotFoundException {
        try {
            Usuario usuario = schiNotesService.consultarUsuarioPorCorreo(correo);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (SchiNotesException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/cuentas/{correo}", method = RequestMethod.GET)
    public ResponseEntity<Cuenta> recursoConsultarCuentaPorCorreo(@PathVariable String correo) throws NotFoundException {
        try {
            Cuenta cuenta = schiNotesService.consultarCuentaPorCorreo(correo);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (SchiNotesException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/cuentas/{correo}/horarios/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<Horario> recursoConsultarHorarioPorNombre(@PathVariable String correo, @PathVariable String nombre) throws NotFoundException {
        try {
            Horario horario = schiNotesService.consultarHorario(correo, nombre);
            return new ResponseEntity<>(horario, HttpStatus.OK);
        } catch (SchiNotesException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }
    
    @RequestMapping(value = "/usuarios/{correo}/horarios/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarHorario(@PathVariable String correo, @PathVariable String nombre) {
        try {
            Horario horario = schiNotesService.consultarHorario(correo, nombre);
            
            return new ResponseEntity<>(horario, HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/registrar", method = RequestMethod.POST)
    public ResponseEntity<?> recursoRegistrarUsuario(@RequestBody Usuario usuario) throws FoundException {
        try {
            schiNotesService.crearCuenta(usuario.getCuentaCorreo());
            schiNotesService.registrarUsuario(usuario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            throw new FoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/horarios", method = RequestMethod.POST)
    public ResponseEntity<?> recursoCrearHorario(@PathVariable String correo, @RequestBody Horario horario) {
        try {
            System.out.println("holaaaaaaaaaaa");
            Usuario usuario = schiNotesService.consultarUsuarioPorCorreo(correo);
            horario.setUsuario(usuario);
            schiNotesService.crearHorario(horario);
            
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(value = "/usuarios/{correo}/horarios/{nombre}/actividades", method = RequestMethod.POST)
    public ResponseEntity<?> recursoRegistrarActividad(@PathVariable String correo, @PathVariable String nombre, Actividad actividad) {
        try {
            schiNotesService.agregarActividad(actividad);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
