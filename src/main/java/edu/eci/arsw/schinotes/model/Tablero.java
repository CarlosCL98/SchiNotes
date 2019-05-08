package edu.eci.arsw.schinotes.model;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Tablero {

    private String nombre;
    private Byte[] template;
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

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

}
