package edu.eci.arsw.schinotes.model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Actividad {
        
    private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private List<Horario> horarios;

    public Actividad() {

    }
    
    public Actividad(int id, String nombre, String descripcion, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
