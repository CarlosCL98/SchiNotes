package edu.eci.arsw.SchiNotes.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@Entity
@Table(name = "nota")
public class Nota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name = "color")
    private String color;

    @Column(name = "template")
    private Byte[] template;

    @Column(name = "fechaCreacion")
    private Date fechaCreacion;

    @ManyToOne
    @JoinColumn
    private Usuario usuario;

    @ManyToOne
    @JoinColumn
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
