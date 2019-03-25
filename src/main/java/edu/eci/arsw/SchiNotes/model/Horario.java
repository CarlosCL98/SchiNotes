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
import javax.persistence.OneToMany;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "DiasPorHorario", joinColumns = {@JoinColumn(name = "horario_nombre") }, inverseJoinColumns = { @JoinColumn(name = "diasdelasemana_id")})
    private List<DiaDeLaSemana> diasDeLaSemana;

    @OneToMany(mappedBy = "horario", cascade = CascadeType.ALL)
    private List<Hora> horas;
    
    public Horario() {

    }

    public Horario(Usuario usuario,String nombre, List<Hora> horas,List<DiaDeLaSemana> diasDeLaSemanas) {
        this.nombre = nombre;
        this.horas = horas;
        this.diasDeLaSemana = diasDeLaSemanas;
        this.usuario = usuario;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the actividades
     */
    public List<Actividad> getActividades() {
        return actividades;
    }

    /**
     * @param actividades the actividades to set
     */
    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    /**
     * @return the diasDeLaSemana
     */
    public List<DiaDeLaSemana> getDiasDeLaSemana() {
        return diasDeLaSemana;
    }

    /**
     * @param diasDeLaSemana the diasDeLaSemana to set
     */
    public void setDiasDeLaSemana(List<DiaDeLaSemana> diasDeLaSemana) {
        this.diasDeLaSemana = diasDeLaSemana;
    }

    /**
     * @return the horas
     */
    public List<Hora> getHoras() {
        return horas;
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(List<Hora> horas) {
        this.horas = horas;
    }

    
    
}
