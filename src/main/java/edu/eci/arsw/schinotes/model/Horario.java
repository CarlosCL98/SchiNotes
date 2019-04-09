package edu.eci.arsw.schinotes.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Daniel Rosales
 * @author Carlos Medina
 */
public class Horario {

    private final String[] DIAS = { "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO" };

    private int id;
    private String nombre;
    private Usuario usuario;
    private Grupo grupo;
    private List<Actividad> actividades;
    private List<DiaDeLaSemana> diasDeLaSemana;
    private List<Hora> horas;
    private int intervaloHoras;
    private int numeroDias;
    private Set<String> horasRepetidas;

    public Horario() {
        this.actividades = new ArrayList<>();
        this.diasDeLaSemana = new ArrayList<>();
        this.horas = new ArrayList<>();
        this.horasRepetidas = new HashSet<>();
    }

    public Horario(int id, String nombre, int intervaloHoras,int numeroDias) {
        this.nombre = nombre;
        this.intervaloHoras = intervaloHoras;
        this.numeroDias = numeroDias;
        // this.horas = this.calcularHoras(intervalo);
        // this.diasDeLaSemana = this.calcularDias(numeroDias);
        //this.usuario = usuario;
        // this.intervaloHoras = intervalo;
        // this.numeroDias = numeroDias;
        this.actividades = new ArrayList<>();
        this.diasDeLaSemana = new ArrayList<>();
        this.horas = new ArrayList<>();
        this.horasRepetidas = new HashSet<>();
    }

    public List<Hora> calcularHoras(int intervalo) {
        this.intervaloHoras = intervalo;
        int currentHora = 1;
        int currentMin = 0;
        String horaString = "";
        Calendar cal = Calendar.getInstance();
        while (!horasRepetidas.contains(horaString)) {
            if(!horaString.equals("")) horasRepetidas.add(horaString);
            cal.set(Calendar.HOUR_OF_DAY, currentHora);
            cal.set(Calendar.MINUTE, currentMin);
            cal.add(Calendar.HOUR, intervalo / 100);
            cal.add(Calendar.MINUTE, (intervalo % 100));
            Date d = cal.getTime();
            horaString = d.toString().substring(10,19);
            
            currentHora = d.getHours();
            currentMin = d.getMinutes();
        }
        for (String ho: horasRepetidas) {
            horas.add(new Hora(ho));
        }
        return horas;
    }

    public List<DiaDeLaSemana> calcularDias(int numeroDias){
        this.numeroDias = numeroDias;
        List<DiaDeLaSemana> dias = new ArrayList<>();
        for (int i = 0; i < numeroDias; i++) {
            dias.add(new DiaDeLaSemana(DIAS[i]));
        }
        return dias;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the nombre to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public int getIntervaloHoras(){
        return this.intervaloHoras;
    }

    public int getNumeroDias(){
        return this.numeroDias;
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

