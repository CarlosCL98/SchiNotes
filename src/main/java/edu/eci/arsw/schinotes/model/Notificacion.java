package edu.eci.arsw.schinotes.model;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Notificacion {
    
    private int id;
    private String descripcion;
    private Usuario usuario;
    
    public Notificacion(){
        
    }

    public Notificacion(int id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
        
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    

    
    
    

   
}
