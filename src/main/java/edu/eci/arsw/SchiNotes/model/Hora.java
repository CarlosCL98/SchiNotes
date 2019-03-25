package edu.eci.arsw.SchiNotes.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "THoras")
public class Hora {

    @Id
    private Date hora;

    @ManyToOne
    @JoinColumn
    private Horario horario;

    public Hora() {

    }

    public Hora(String hora) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:MM:SS");
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
    
}
