package edu.eci.arsw.SchiNotes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@Entity
@Table(name = "horario")
public class Horario {

    @Id
    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn
    private Usuario usuario;

    @OneToOne(mappedBy = "horarioNombre")
    private Grupo grupo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "ActividadPorHorario", joinColumns = {@JoinColumn(name = "horario_nombre") }, inverseJoinColumns = { @JoinColumn(name = "actividad_id")})
    private List<Actividad> actividades;
    
    /**
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return el usuario_identificacion
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param nombre el nombre a actualizar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param usuario el usuario_identificacion a actualizar
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
