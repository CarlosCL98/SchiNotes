package edu.eci.arsw.SchiNotes.exceptions;

public class SchiNotesPersistenceException extends Exception {

    private static final long serialVersionUID = 1L;

    public SchiNotesPersistenceException(String mensaje) {
        super(mensaje);
    }
}