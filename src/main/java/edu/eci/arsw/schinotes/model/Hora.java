package edu.eci.arsw.schinotes.model;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Hora {

    private String hora;
    private Horario horario;

    public Hora() {

    }

    public Hora(String hora) {
        
        this.hora = hora;        
        /*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        java.util.Date date;
        try {
            date = sdf.parse(hora);
            System.out.println("estoy adentro de HORA"+date);
            this.hora = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    public Horario obtenerHorario(){
        return this.horario;
    }
    
}
