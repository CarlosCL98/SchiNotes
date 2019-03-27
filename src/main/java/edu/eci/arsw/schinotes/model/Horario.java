package edu.eci.arsw.schinotes.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Horario {

    private final int HORA_LIMITE = 24;
    private final String[] DIAS = {"LUNES","MARTES","MIERCOLES","JUEVES","VIERNES","SABADO","DOMINGO"};

    private String nombre;
    private Usuario usuario;
    private Grupo grupo;
    private List<Actividad> actividades;
    private List<DiaDeLaSemana> diasDeLaSemana;
    private List<Hora> horas;
    
    public Horario() {

    }

    public Horario(Usuario usuario,String nombre, int intervalo,int numeroDias) {
        this.nombre = nombre;
        this.horas = calcularHoras(intervalo);
        this.diasDeLaSemana = calcularDias(numeroDias);
        this.usuario = usuario;
    }

    private List<Hora> calcularHoras(int intervalo){
        List<Hora> horas = new ArrayList<>();
        int currentHora = 1;
        int currentMin = 0;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        while(currentHora < HORA_LIMITE){
            cal.set(Calendar.HOUR_OF_DAY,currentHora);
            cal.set(Calendar.MINUTE,currentMin);
            cal.add(Calendar.HOUR, intervalo/100);
            cal.add(Calendar.MINUTE, (intervalo%100));
            Date d = cal.getTime();
            horas.add(new Hora(dateFormat.format(d)));
            currentHora = d.getHours();
            currentMin = d.getMinutes();
        }
        return horas;
    }

    private List<DiaDeLaSemana> calcularDias(int numeroDias){
        List<DiaDeLaSemana> dias = new ArrayList<>();
        for (int i = 0; i < numeroDias; i++) {
            dias.add(new DiaDeLaSemana(DIAS[i]));
        }
        return dias;
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

    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the actividades
     */
    public List<Actividad> getActividades() {
        return actividades;
    }

    /**
     * @param actividades the actividades to set
     */
    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    /**
     * @return the diasDeLaSemana
     */
    public List<DiaDeLaSemana> getDiasDeLaSemana() {
        return diasDeLaSemana;
    }

    /**
     * @param diasDeLaSemana the diasDeLaSemana to set
     */
    public void setDiasDeLaSemana(List<DiaDeLaSemana> diasDeLaSemana) {
        this.diasDeLaSemana = diasDeLaSemana;
    }

    /**
     * @return the horas
     */
    public List<Hora> getHoras() {
        return horas;
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(List<Hora> horas) {
        this.horas = horas;
    }

    
    
}
