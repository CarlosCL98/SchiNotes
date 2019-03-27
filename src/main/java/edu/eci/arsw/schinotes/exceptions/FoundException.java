package edu.eci.arsw.schinotes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author carloscl
 */
@ResponseStatus(HttpStatus.FOUND)
public class FoundException extends Exception {

    public FoundException(String mensaje) {
        super(mensaje);
    }

}
