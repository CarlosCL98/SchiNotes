package edu.eci.arsw.schinotes.model;

import java.sql.Date;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Actividad {

    private int id;
    private String nombre;
    private String descripcion;
    private String fecha;
    private int horario_id;
    private String dia;
    private String hora_ini;
    private String hora_fin;

    public Actividad() {
    }

    public Actividad(int id, String nombre, String descripcion, String fecha, int horario_id, String dia, String hora_ini,String hora_fin) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.horario_id = horario_id;
        this.dia = dia;
        this.hora_ini = hora_ini;
        this.hora_fin = hora_fin;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getHorario_id() {
        return horario_id;
    }

    public void setHorario_id(int horario_id) {
        this.horario_id = horario_id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora_ini() {
        return hora_ini;
    }

    public void setHora_ini(String hora_ini) {
        this.hora_ini = hora_ini;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    @Override
    public String toString() {
        return "Actividad{" + "id=" + this.id + ", nombre=" + this.nombre + ", descripcion=" + this.descripcion
                + ", fecha=" + this.fecha + ", horario_id=" + this.horario_id + ", dia=" + this.dia + ", hora_ini="
                + this.hora_ini + ", hora_fin=" + this.hora_fin + "}";
    }
}
