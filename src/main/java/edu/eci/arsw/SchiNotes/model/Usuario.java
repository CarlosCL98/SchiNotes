package edu.eci.arsw.SchiNotes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int identificacion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "foto")
    private Byte[] foto;

    @Column(name = "intereses")
    private String intereses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cuenta_correo", unique = true)
    private Cuenta cuentaCorreo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "amigo", joinColumns = {@JoinColumn(name = "usuario_identificacion") }, inverseJoinColumns = {@JoinColumn(name = "usuario_2_identificacion")})
    private List<Usuario> misAmigos;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "misAmigos")
    private List<Usuario> deLosQueSoyAmigo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "GrupoDeTrabajo", joinColumns = {@JoinColumn(name = "usuario_identificacion") }, inverseJoinColumns = {@JoinColumn(name = "grupo_identificacion")})
    private List<Grupo> grupos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Nota> notas;

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    private List<Horario> horarios;

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
}
