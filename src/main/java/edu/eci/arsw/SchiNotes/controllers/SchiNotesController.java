package edu.eci.arsw.SchiNotes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.SchiNotes.model.Horario;
import edu.eci.arsw.SchiNotes.services.SchiNotesService;


/**
 *
 * @author cristian
 */
@RestController
@RequestMapping(value = "/horarios")
public class SchiNotesController {
 	
    @Autowired
    SchiNotesService horarioService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> recursoGetAllCinemas(){
	    try {	    	
	        List<Horario> data = horarioService.consultarHorarios();
			return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
	    } catch (Exception ex) {	        
	        return new ResponseEntity<>("Error Cinemas no encontrados", HttpStatus.NOT_FOUND);
	    }        
    }
}