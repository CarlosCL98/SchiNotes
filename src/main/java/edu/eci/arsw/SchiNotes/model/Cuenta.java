package edu.eci.arsw.SchiNotes.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 *  @author Daniel Rosales
 * @author Carlos Medina
 */
@Entity
@Table(name = "cuenta")
public class Cuenta {
    
    @Id            
    private String correo;

    @Column(name = "contraseña")
    private String contraseña;

    @Column(name = "nickname")
    private String nickname;

    @OneToOne(mappedBy = "cuentaCorreo", cascade = CascadeType.ALL)
    private Usuario usuario;

    public Cuenta(String correo, String contraseña, String nickname) {
        this.correo = correo;
        this.contraseña = contraseña;
        this.nickname = nickname;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
