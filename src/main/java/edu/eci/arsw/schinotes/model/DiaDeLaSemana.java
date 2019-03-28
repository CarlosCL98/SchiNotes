package edu.eci.arsw.schinotes.model;

import java.util.List;

public class DiaDeLaSemana  {

    private String nombre;

    public DiaDeLaSemana(){

    }
    
    public DiaDeLaSemana(String nombre){
        this.nombre = nombre;
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