package edu.eci.arsw.SchiNotes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.SchiNotes.model.Horario;
import edu.eci.arsw.SchiNotes.model.Usuario;
import edu.eci.arsw.SchiNotes.services.SchiNotesService;


/**
 *
 * @author cristian
 */
@RestController
@RequestMapping(value = "/schinotes")
@Service
public class SchiNotesController {
 	
    @Autowired
    SchiNotesService schiNotesService;
	
@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> recursoRegisterUser(@RequestBody Usuario usuario){
		System.out.println("aqui debe imprimirrrrrr");
		System.out.println(usuario.getCuentaCorreo().getCorreo());
	    try {	    	
	        schiNotesService.registrarUsuario(usuario);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
	    } catch (Exception ex) {
			//ex.printStackTrace();	        
	        return new ResponseEntity<>("Error no fue posible crear el usuario", HttpStatus.FORBIDDEN);
	    }        
    }


/*@RequestMapping(value ="/{identificacion}",method = RequestMethod.GET)
	public ResponseEntity<?> recursoRegisterUser(@PathVariable int identificacion){
	    try {	    	
	        Usuario user = schiNotesService.consultarUsuario(identificacion);
			return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
	    } catch (Exception ex) {	        
	        return new ResponseEntity<>("Error no fue posible crear el usuario", HttpStatus.FORBIDDEN);
	    }        
    }*/
}