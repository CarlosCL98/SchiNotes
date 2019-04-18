package edu.eci.arsw.schinotes.controllers;

import edu.eci.arsw.schinotes.exceptions.*;
import edu.eci.arsw.schinotes.model.Actividad;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Horario;
import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.services.EmailService;
import edu.eci.arsw.schinotes.services.SchiNotesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@RestController
@RequestMapping(value = "/schinotes")
public class SchiNotesController {

    @Autowired
    SchiNotesService schiNotesService;

    @Autowired
    EmailService emailService;

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
    public ResponseEntity<Usuario> recursoConsultarUsuarioPorCorreo(@PathVariable String correo)
            throws NotFoundException {
        try {
            Usuario usuario = schiNotesService.consultarUsuarioPorCorreo(correo);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (SchiNotesException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/cuentas/{correo}", method = RequestMethod.GET)
    public ResponseEntity<Cuenta> recursoConsultarCuentaPorCorreo(@PathVariable String correo)
            throws NotFoundException {
        try {
            Cuenta cuenta = schiNotesService.consultarCuentaPorCorreo(correo);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (SchiNotesException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/cuentas/{correo}/horarios/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<Horario> recursoConsultarHorarioPorNombre(@PathVariable String correo,
            @PathVariable String nombre) throws NotFoundException {
        try {
            Horario horario = schiNotesService.consultarHorarioByName(correo, nombre);
            return new ResponseEntity<>(horario, HttpStatus.OK);
        } catch (SchiNotesException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/horarios/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarHorarioUsuarioByName(@PathVariable String correo, @PathVariable String nombre) {
        try {
            Horario horario = schiNotesService.consultarHorarioByName(correo, nombre);
            return new ResponseEntity<>(horario, HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/horarios/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarHorarioById(@PathVariable String correo, @PathVariable int id) {
        try {
            Horario horario = schiNotesService.consultarHorarioById(correo, id);
            return new ResponseEntity<>(horario, HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/horarios", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarHorarios(@PathVariable String correo) {
        try {

            List<Horario> horarios = schiNotesService.consultarHorarios(correo);
            return new ResponseEntity<>(horarios, HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/horarios/{nombre}/actividades/{actividad}", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarActividad(@PathVariable String correo, @PathVariable String nombre,
            @PathVariable String actividad) {
        try {
            Actividad actividadConsultada = schiNotesService.consultarActividad(correo, nombre, actividad);
            return new ResponseEntity<>(actividadConsultada, HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/horarios/{nombre}/actividades", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarActividades(@PathVariable String correo, @PathVariable String nombre) {
        try {
            List<Actividad> actividades = schiNotesService.consultarActividades(correo, nombre);
            return new ResponseEntity<>(actividades, HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/amigos", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarAmigo(@PathVariable String correo) {
        try {
            return new ResponseEntity<>(schiNotesService.consultarAmigos(correo), HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/busqueda/{correoPersona}", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultaIncompleta(@PathVariable String correoPersona) {
        try {
            return new ResponseEntity<>(schiNotesService.consultarPersonasIncompleta(correoPersona), HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/registrar", method = RequestMethod.POST)
    public ResponseEntity<?> recursoRegistrarUsuario(@RequestBody Usuario usuario) throws FoundException {
        try {
            SchiNotesThread enviarCorreo = new SchiNotesThread(usuario.getCuentaCorreo().getCorreo(),
                    "Usuario creado exitosamente",
                    "Hola " + usuario.getCuentaCorreo().getNickname() + ".\n"
                            + "Su usuario de SchiNotes ha sido creado satisfactoriamente.\n"
                            + "Ahora puede ingresar a http://schinotes.herokuapp.com/ y aprovechar la herramienta.\n\n"
                            + "Si tienes alguna inquietud, puedes comunicarte a schinotes2019dc@gmail.com\n\n"
                            + "Atentamente,\nEl staff de SchiNotes",
                    emailService);
            enviarCorreo.start();
            schiNotesService.crearCuenta(usuario.getCuentaCorreo());
            schiNotesService.registrarUsuario(usuario);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            throw new FoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/horarios", method = RequestMethod.POST)
    public ResponseEntity<?> recursoCrearHorario(@PathVariable String correo, @RequestBody Horario horario) {
        try {
            System.out.println("numero de dias: " + horario.getNumeroDias());
            System.out.println("intervalo horas: " + horario.getIntervaloHoras());
            Usuario usuario = schiNotesService.consultarUsuarioPorCorreo(correo);
            horario.setUsuario(usuario);
            schiNotesService.crearHorario(horario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/horarios/{id}/actividades", method = RequestMethod.POST)
    public ResponseEntity<?> recursoRegistrarActividad(@PathVariable String correo, @PathVariable int id,
            @RequestBody Actividad actividad) {
        try {
            System.out.println(actividad.toString());
            actividad.setHorario_id(id);
            schiNotesService.agregarActividad(actividad);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/amigos", method = RequestMethod.POST)
    public ResponseEntity<?> recursoRegistrarAmigo(@PathVariable String correo, @RequestBody Usuario amigo) {
        try {
            schiNotesService.agregarAmigo(correo, amigo.getCuentaCorreo().getCorreo());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
