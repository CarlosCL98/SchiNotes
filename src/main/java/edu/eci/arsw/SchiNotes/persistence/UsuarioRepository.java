package edu.eci.arsw.SchiNotes.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.eci.arsw.SchiNotes.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u JOIN Cuenta cu ON (u.cuentaCorreo = cu.correo) WHERE u.cuentaCorreo = ':correo'")
    Usuario findUserByEmail(@Param("correo") String correo);
    
}