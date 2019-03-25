package edu.eci.arsw.SchiNotes.model;

import javax.persistence.CascadeType;
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
@Table(name = "cuenta")
public class Cuenta {
    
    @Id            
    private String correo;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "nickname")
    private String nickname;

    @OneToOne(mappedBy = "cuentaCorreo", cascade = CascadeType.ALL)
    private Usuario usuario;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
     
}

