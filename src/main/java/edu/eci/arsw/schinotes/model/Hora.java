package edu.eci.arsw.schinotes.model;

public class Hora {

    private String hora;
    private Horario horario;

    public Hora() {

    }

    public Hora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    public Horario obtenerHorario(){
        return this.horario;
    }
    
}
