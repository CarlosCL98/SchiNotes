package edu.eci.arsw.schinotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.schinotes.model.Actividad;

/**
 * 
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@Controller
public class SchiNotesMessagesHandler {

    @Autowired
    private SimpMessagingTemplate msgt;

    @MessageMapping("/horario.{idHorario}")
    public void handlePointEvent(Actividad actividad, @DestinationVariable int idHorario) throws Exception {
        System.out.println("Recibiendo la información de " + idHorario);
        msgt.convertAndSend("/topic/horario." + idHorario, actividad);
    }

}