package edu.eci.arsw.schinotes.controllers;

import edu.eci.arsw.schinotes.exceptions.*;
import edu.eci.arsw.schinotes.model.Actividad;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Grupo;
import edu.eci.arsw.schinotes.model.Horario;
import edu.eci.arsw.schinotes.model.Notificacion;
import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.services.EmailService;
import edu.eci.arsw.schinotes.services.SchiNotesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private Map<String, String> codigosComprobacion = new HashMap<>();

    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> recursoConsultarUsuarios() throws NotFoundException {
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
    public ResponseEntity<?> recursoConsultarHorarioUsuarioByName(@PathVariable String correo,
            @PathVariable String nombre) {
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

    @RequestMapping(value = "/actividades/{actividadId}", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarActividadPorId(@PathVariable int actividadId) {
        try {
            Actividad actividadConsultada = schiNotesService.consultarActividadById(actividadId);
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

    @RequestMapping(value = "/grupos/{grupoId}/horarios/{nombreHorario}", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarActividadesPorGrupo(@PathVariable int grupoId,
            @PathVariable String nombreHorario) {
        try {
            List<Actividad> actividades = schiNotesService.consultarActividadesPorGrupo(grupoId, nombreHorario);
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
            return new ResponseEntity<>(schiNotesService.consultarPersonasIncompleta(correoPersona),
                    HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/grupos", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarGrupos(@PathVariable String correo) {
        try {
            List<Grupo> grupos = schiNotesService.consultarGruposDeUnUsuario(correo);
            return new ResponseEntity<>(grupos, HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/comprobar", method = RequestMethod.GET)
    public ResponseEntity<?> recursoComprobarRegistro(@PathVariable String correo) throws NotFoundException {
        try {
            if (!codigosComprobacion.containsKey(correo)) {
                throw new SchiNotesException("El correo '" + correo + "' no ha sido registrado aún.");
            }
            String codigoComprobacion = codigosComprobacion.get(correo);
            return new ResponseEntity<>(codigoComprobacion, HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/cuentas/verificar", method = RequestMethod.GET)
    public ResponseEntity<?> recursoCuentaEstaVerificada(@PathVariable String correo) {
        try {
            boolean estaVerficada = schiNotesService.cuentaEstaVerificada(correo);
            System.out.println(estaVerficada);
            return new ResponseEntity<>(estaVerficada, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/grupos/{idGrupo}/horarios", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarHorarioGrupo(@PathVariable int idGrupo) {
        try {
            Horario horario = schiNotesService.consultarHorarioPorGrupo(idGrupo);
            return new ResponseEntity<>(horario, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/grupos", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarTodosLosGrupos() {
        try {
            List<Grupo> grupos = schiNotesService.consultarTodosLosGrupos();
            return new ResponseEntity<>(grupos, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/notificaciones", method = RequestMethod.GET)
    public ResponseEntity<?> recursoConsultarLasNotificaciones(@PathVariable String correo) {
        try {
            List<Notificacion> notificaciones = schiNotesService.consultarNotificaciones(correo);
            return new ResponseEntity<>(notificaciones, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/usuarios/registrar", method = RequestMethod.POST)
    public ResponseEntity<?> recursoRegistrarUsuario(@RequestBody Usuario usuario) throws FoundException {
        try {
            String codigoComprobacion = randomAlphaNumeric(10);
            codigosComprobacion.put(usuario.getCuentaCorreo().getCorreo(), codigoComprobacion);
            SchiNotesThread enviarCorreo = new SchiNotesThread(usuario.getCuentaCorreo().getCorreo(),
                    "Usuario creado exitosamente",
                    "Hola " + usuario.getCuentaCorreo().getNickname() + ".\n"
                            + "Su usuario de SchiNotes ha sido creado satisfactoriamente.\n\n"
                            + "Para confirmar que usted es quien se registró, ingrese el siguiente código en el campo que apareció inmediantemente después de registrarse: "
                            + codigoComprobacion + "\n\n"
                            + "Luego de verficada su cuenta, puede ingresar a http://schinotes.herokuapp.com/login.html y aprovechar la herramienta.\n\n"
                            + "Si tiene alguna inquietud, puede comunicarse a schinotes2019dc@gmail.com\n\n"
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

    @RequestMapping(value = "/usuarios/{correo}/verificado", method = RequestMethod.POST)
    public ResponseEntity<?> recursoVerificarCuenta(@PathVariable String correo) throws NotFoundException {
        try {
            schiNotesService.verificarCuenta(correo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/horarios", method = RequestMethod.POST)
    public ResponseEntity<?> recursoCrearHorario(@PathVariable String correo, @RequestBody Horario horario) {
        try {
            Usuario usuario = schiNotesService.consultarUsuarioPorCorreo(correo);
            horario.setUsuario(usuario);
            schiNotesService.crearHorario(horario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/horarios/{id}/actividades", method = RequestMethod.POST)
    public ResponseEntity<?> recursoRegistrarActividad(@PathVariable int id, @RequestBody Actividad actividad) {
        try {
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

    @RequestMapping(value = "/usuarios/{correo}/grupos", method = RequestMethod.POST)
    public ResponseEntity<?> recursoCrearGrupo(@PathVariable String correo, @RequestBody Grupo grupo) {
        try {
            schiNotesService.crearGrupo(correo, grupo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/grupos/{idGrupo}/integrantes/{correo}/horarios/{nombreHorario}", method = RequestMethod.POST)
    public ResponseEntity<?> recursoAgregarIntegrante(@PathVariable int idGrupo, @PathVariable String correo,
            @PathVariable String nombreHorario) {
        try {
            Usuario integrante = schiNotesService.consultarUsuarioPorCorreo(correo);
            Horario horario = schiNotesService.consultarHorarioByName(correo, nombreHorario);
            schiNotesService.agregarNuevoIntegrante(idGrupo, integrante, horario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/usuarios/{correo}/notificaciones", method = RequestMethod.POST)
    public ResponseEntity<?> recursoAgregarNotificacion(@PathVariable String correo, @RequestBody Notificacion notificacion) {
        try {
            schiNotesService.agregarNotificacion(correo, notificacion);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/grupos/{grupoId}/integrantes/{correo}", method = RequestMethod.DELETE)
    public ResponseEntity<?> recursoAgregarNotificacion(@PathVariable int grupoId, @PathVariable String correo) {
        try {
            schiNotesService.eliminarIntegranteDeGrupo(grupoId, correo);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (SchiNotesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
        }
    }   


    private static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
