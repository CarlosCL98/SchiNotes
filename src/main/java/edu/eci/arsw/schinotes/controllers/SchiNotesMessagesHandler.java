package edu.eci.arsw.schinotes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.schinotes.model.Actividad;
import edu.eci.arsw.schinotes.model.Notificacion;

/**
 * 
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@Controller
public class SchiNotesMessagesHandler {

    @Autowired
    private SimpMessagingTemplate msgt;

    // El primer entero corresponde al id del grupo, y el siguiente a una lista con
    // los usuarios conectados a un chat.
    private ConcurrentHashMap<String, List<String>> conectadosAlChat = new ConcurrentHashMap<>();

    @MessageMapping("/horario.{idHorario}")
    public void handlePointEventHorario(Actividad actividad, @DestinationVariable String idHorario) throws Exception {
        msgt.convertAndSend("/topic/horario." + idHorario, actividad);
    }

    @MessageMapping("/horarioGrupo.{idGrupo}")
    public void handlePointEventHorarioGrupo(Actividad actividad, @DestinationVariable String idGrupo) throws Exception {
        msgt.convertAndSend("/topic/horarioGrupo." + idGrupo, actividad);
    }

    @MessageMapping("/notificacion.{idGrupo}")
    public void handlePointEventNotificacion(Notificacion notificacion, @DestinationVariable String idGrupo) throws Exception {
        System.out.println("entre al messageHandler");
        System.out.println(notificacion.getDescripcion());
        msgt.convertAndSend("/topic/grupo." + idGrupo, notificacion);
    }

    @MessageMapping("/conectado.{idGrupo}")
    public void handlePointEventConectar(String correoUsuarioConectado, @DestinationVariable String idGrupo)
            throws Exception {
        if (conectadosAlChat.containsKey(idGrupo)) {
            List<String> usuariosConectados = conectadosAlChat.get(idGrupo);
            boolean estaConectado = false;
            for (int i = 0; i < usuariosConectados.size() && !estaConectado; i++) {
                if (correoUsuarioConectado.equals(usuariosConectados.get(i))) {
                    estaConectado = true;
                }
            }
            if (!estaConectado) {
                usuariosConectados.add(correoUsuarioConectado);
                conectadosAlChat.replace(idGrupo, usuariosConectados);
            }
        } else {
            List<String> usuariosConectados = new ArrayList<>();
            usuariosConectados.add(correoUsuarioConectado);
            conectadosAlChat.put(idGrupo, usuariosConectados);
        }
        msgt.convertAndSend("/topic/conectado." + idGrupo, conectadosAlChat.get(idGrupo).size());
    }

    @MessageMapping("/desconectar.{idGrupo}")
    public void handlePointEventDesconectar(String correoUsuarioDesconectado, @DestinationVariable String idGrupo)
            throws Exception {
        if (conectadosAlChat.containsKey(idGrupo)) {
            List<String> usuariosConectados = conectadosAlChat.get(idGrupo);
            if (usuariosConectados.contains(correoUsuarioDesconectado)) {
                usuariosConectados.remove(correoUsuarioDesconectado);
                conectadosAlChat.replace(idGrupo, usuariosConectados);
            }
        }
        msgt.convertAndSend("/topic/desconectar." + idGrupo, conectadosAlChat.get(idGrupo).size());
    }

}