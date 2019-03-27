package edu.eci.arsw.schinotes.dao;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;

/**
 *
 * @author carloscl
 */
public interface CuentaDAO {
    
    public Cuenta loadCuentaByEmail(String correo) throws SchiNotesException;

    public void saveCuenta(Cuenta cuenta) throws SchiNotesException;

}
