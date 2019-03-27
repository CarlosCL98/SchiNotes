package edu.eci.arsw.schinotes.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Usuario implements Serializable {

    private int identificacion;
    private String nombre;
    private String apellido;
    private Byte[] foto;
    private String intereses;
    private Cuenta cuentaCorreo;
    private List<Usuario> misAmigos;
    private List<Usuario> deLosQueSoyAmigo;

    public Usuario(){
        
    }

    public Usuario(String nombre, String apellido, Cuenta cuentaCorreo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuentaCorreo = cuentaCorreo;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }

    public String getIntereses() {
        return intereses;
    }

    public void setIntereses(String intereses) {
        this.intereses = intereses;
    }

    public void setCuentaCorreo(Cuenta cuentaCorreo) {
        this.cuentaCorreo = cuentaCorreo;
    }

    public Cuenta getCuentaCorreo() {
        return cuentaCorreo;
    }

    public List<Usuario> getMisAmigos() {
        return misAmigos;
    }

    public void setMisAmigos(List<Usuario> misAmigos) {
        this.misAmigos = misAmigos;
    }

    public List<Usuario> getDeLosQueSoyAmigo() {
        return deLosQueSoyAmigo;
    }

    public void setDeLosQueSoyAmigo(List<Usuario> deLosQueSoyAmigo) {
        this.deLosQueSoyAmigo = deLosQueSoyAmigo;
    }
    
    @Override
    public String toString() {
        return "Usuario{" + "id=" + this.identificacion + ", nombre=" + this.nombre + ", apellido=" + this.apellido + ", foto=" + Arrays.toString(this.foto) + ", intereses=" + this.intereses + ", cuenta=" + this.cuentaCorreo + "}";
    }
}
