package edu.eci.arsw.SchiNotes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@Entity
@Table(name = "grupo")        
public class Grupo {
    
    @Id
    @Column(name = "identificacion")
    private int identificacion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Horario horarioNombre;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Tablero tableroNombre;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "grupos")
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Nota> notas;

    public Grupo() {

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
}
