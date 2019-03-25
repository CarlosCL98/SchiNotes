/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.SchiNotes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@Entity
@Table(name = "tablero")
public class Tablero {
    
    @Id
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "template")
    private Byte[] template;

    @OneToOne(mappedBy = "tableroNombre")
    private Grupo grupo;

    public Tablero() {

    }
    
    public Tablero(String nombre, Byte[] template) {
        this.nombre = nombre;
        this.template = template;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Byte[] getTemplate() {
        return template;
    }

    public void setTemplate(Byte[] template) {
        this.template = template;
    }
    
    
    
    
}
