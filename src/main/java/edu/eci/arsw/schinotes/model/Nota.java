package edu.eci.arsw.schinotes.model;

import java.sql.Date;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Nota {
    
    private int id;
    private String color;
    private Byte[] template;
    private Date fechaCreacion;
    private Usuario usuario;
    private Grupo grupo;

    public Nota() {

    }
    
    public Nota(int id, String color, Byte[] template, Date fechaCreacion, Usuario usuario, Grupo grupo) {
        this.id = id;
        this.color = color;
        this.template = template;
        this.fechaCreacion = fechaCreacion;
        this.usuario = usuario;
        this.grupo = grupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Byte[] getTemplate() {
        return template;
    }

    public void setTemplate(Byte[] template) {
        this.template = template;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }    
}
