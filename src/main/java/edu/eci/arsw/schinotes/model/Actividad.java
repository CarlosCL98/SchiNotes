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
    private String horario;
    private String dia;
    private String hora;

    public Actividad() {
    }

    public Actividad(int id, String nombre, String descripcion, Date fecha, String horario, String dia, String hora) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.horario = horario;
        this.dia = dia;
        this.hora = hora;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Actividad{" + "id=" + this.id + ", nombre=" + this.nombre + ", descripcion=" + this.descripcion + ", fecha=" + this.fecha + ", horario=" + this.horario + ", dia=" + this.dia + ", hora=" + this.hora + "}";
    }
}
