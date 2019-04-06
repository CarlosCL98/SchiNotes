package edu.eci.arsw.schinotes.controllers;

import edu.eci.arsw.schinotes.services.EmailService;

public class SchiNotesThread extends Thread {

    private EmailService emailService;
    private String correo;
    private String subject;
    private String text;

    public SchiNotesThread(String correo, String subject, String text, EmailService emailService) {
        this.correo = correo;
        this.subject = subject;
        this.text = text;
        this.emailService = emailService;
    }

    @Override
    public void run() {
        emailService.sendSimpleMessage(correo, subject, text);        
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
}