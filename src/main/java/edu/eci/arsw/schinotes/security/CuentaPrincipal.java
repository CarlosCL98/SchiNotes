package edu.eci.arsw.schinotes.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.eci.arsw.schinotes.model.Cuenta;

public class CuentaPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Cuenta user;

    public CuentaPrincipal(Cuenta user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getCorreo();
    }

    @Override
    public String getPassword() {
        return user.getContrasena();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Cuenta getUser() {
        return user;
    }

}