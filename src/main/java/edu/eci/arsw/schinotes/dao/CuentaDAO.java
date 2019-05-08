package edu.eci.arsw.schinotes.dao;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;

/**
 *
 * @author carloscl
 */
public interface CuentaDAO {

    /**
     * Se consulta y retorna una cuenta de usuario dado el correo.
     *
     * @param correo
     * @return La cuenta consultada.
     * @throws SchiNotesException
     */
    public Cuenta loadCuentaByEmail(String correo) throws SchiNotesException;

    /**
     * Se guarda una cuenta cuando un usuario se esta registrando en la
     * herramienta.
     *
     * @param cuenta
     * @throws SchiNotesException
     */
    public void saveCuenta(Cuenta cuenta) throws SchiNotesException;

    /**
     * Se actualiza la cuenta, pues ya se encuentra verificada.
     * 
     * @param correo
     */
    public void updateVerificadaCuentaByCorreo(String correo);
    
    /**
     * Se obtiene si una cuenta está verificada o no.
     * 
     * @param correo
     * @return un booleano indicando si la cuenta está verificada o no.
     */
    public boolean loadBoolCuentaVerificada(String correo);

}
