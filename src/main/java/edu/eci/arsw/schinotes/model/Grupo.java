package edu.eci.arsw.schinotes.model;

import java.util.List;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Grupo {

    private int identificacion;
    private String nombre;
    private String descripcion;
    private Horario horarioNombre;
    private Tablero tableroNombre;
    private List<Usuario> usuarios;
    private List<Nota> notas;

    public Grupo() {

    }

    public Grupo(int identificacion, String nombre, String descripcion) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Grupo(int identificacion, String nombre, String descripcion, Horario horarioNombre, Tablero tableroNombre) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioNombre = horarioNombre;
        this.tableroNombre = tableroNombre;
    }

    public int getidentificacion() {
        return identificacion;
    }

    public void setidentificacion(int identificacion) {
        this.identificacion = identificacion;
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

    public Horario getHorarioNombre() {
        return horarioNombre;
    }

    public void setHorarioNombre(Horario horarioNombre) {
        this.horarioNombre = horarioNombre;
    }

    public Tablero getTableroNombre() {
        return tableroNombre;
    }

    public void setTableroNombre(Tablero tableroNombre) {
        this.tableroNombre = tableroNombre;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
