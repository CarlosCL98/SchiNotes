package edu.eci.arsw.schinotes.model;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Cuenta {
    
    private String correo;
    private String contrasena;
    private String nickname;
    private boolean verificada;

    public Cuenta() {
        
    }

    public Cuenta(String correo, String contrasena, String nickname) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.nickname = nickname;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }
    
    public boolean getVerificada() {
        return verificada;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "correo=" + this.correo + ", contraseña=" + this.contrasena + ", nickname=" + this.nickname + ", verificada=" + this.verificada + "}";
    }
     
}

