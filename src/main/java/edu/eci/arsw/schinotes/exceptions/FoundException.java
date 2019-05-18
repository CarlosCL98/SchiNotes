package edu.eci.arsw.schinotes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Daniel Rosales
 * @author Carlos Medina
 */
@ResponseStatus(HttpStatus.FOUND)
public class FoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public FoundException(String mensaje) {
        super(mensaje);
    }

}
