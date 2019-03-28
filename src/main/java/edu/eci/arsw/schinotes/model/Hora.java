package edu.eci.arsw.schinotes.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Hora {

    private Date hora;
    private Horario horario;

    public Hora() {

    }

    public Hora(String hora) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        java.util.Date date;
        try {
            date = sdf.parse(hora);
            Date sqlStartDate = new Date(date.getTime());
            this.hora = sqlStartDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the hora
     */
    public Date getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Horario obtenerHorario(){
        return this.horario;
    }
    
}
