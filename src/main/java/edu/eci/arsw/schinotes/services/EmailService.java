package edu.eci.arsw.schinotes.services;


public interface EmailService {

    public void sendSimpleMessage(String to, String subject, String text);
    
}