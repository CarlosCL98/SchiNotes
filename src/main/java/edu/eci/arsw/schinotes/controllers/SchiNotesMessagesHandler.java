package edu.eci.arsw.schinotes.controllers;

import java.util.concurrent.ConcurrentHashMap;

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

    // El primer entero corresponde al id del grupo, y el siguiente a la cantidad de conectados a un chat.
    private ConcurrentHashMap<Integer, Integer> conectados = new ConcurrentHashMap<>();

    @MessageMapping("/horario.{idHorario}")
    public void handlePointEvent(Actividad actividad, @DestinationVariable int idHorario) throws Exception {
        msgt.convertAndSend("/topic/horario." + idHorario, actividad);
    }

    @MessageMapping("/conectado.{idGrupo}")
    public void handlePointEvent(int conectado, @DestinationVariable int idGrupo) throws Exception {
        if (conectados.containsKey(idGrupo)) {
            int conect = conectados.get(idGrupo);
            conect = conect + conectado;
            conectados.replace(idGrupo, conect);
        } else {
            conectados.put(idGrupo, conectado);
        }
        msgt.convertAndSend("/topic/conectado." + idGrupo, conectados.get(idGrupo));
    }

}