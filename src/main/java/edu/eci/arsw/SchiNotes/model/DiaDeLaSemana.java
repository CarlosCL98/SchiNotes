package edu.eci.arsw.SchiNotes.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "DiasDeLaSemana")
public class DiaDeLaSemana  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "diasDeLaSemana")
    private List<Horario> horarios;

    public DiaDeLaSemana(){

    }
    
    public DiaDeLaSemana(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
}