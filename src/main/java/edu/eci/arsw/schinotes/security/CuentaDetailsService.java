package edu.eci.arsw.schinotes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.eci.arsw.schinotes.exceptions.SchiNotesException;
import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.services.SchiNotesService;

@Service
public class CuentaDetailsService implements UserDetailsService {

    @Autowired
    private SchiNotesService userRepository;

    public CuentaDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        Cuenta user;
        try {
            user = userRepository.consultarCuentaPorCorreo(username);
        } catch (SchiNotesException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CuentaPrincipal(user);
    }

}