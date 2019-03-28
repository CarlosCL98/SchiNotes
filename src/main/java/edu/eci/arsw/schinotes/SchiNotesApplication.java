package edu.eci.arsw.schinotes;

import edu.eci.arsw.schinotes.model.Cuenta;
import edu.eci.arsw.schinotes.model.Usuario;
import edu.eci.arsw.schinotes.services.SchiNotesService;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author carlo
 */
@SpringBootApplication
@ComponentScan(basePackages = "edu.eci.arsw.schinotes")
@RestController
public class SchiNotesApplication implements CommandLineRunner {
    
    @Autowired
    DataSource dataSource;
    
    @Autowired
    private SchiNotesService schiNotesService;
    
    public static void main(String[] args) {
        SpringApplication.run(SchiNotesApplication.class, args);
    }
    
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Connection Polling datasource : "+ dataSource);
        /*
        System.out.println(schiNotesService.consultarUsuarios());
        System.out.println(schiNotesService.consultarUsuarioPorCorreo("a@m.com"));
        //Aqui se debería mostrar una excepción si el usuario no está creado.
        System.out.println(schiNotesService.consultarUsuarioPorCorreo("camer.cl98@gmail.com"));
        Cuenta cuentaCorreo1 = new Cuenta("camer.cl98@gmail.com", "12345", "CarlosCL98");
        schiNotesService.crearCuenta(cuentaCorreo1);
        Usuario usuario1 = new Usuario("Carlos", "Medina", cuentaCorreo1);
        schiNotesService.registrarUsuario(usuario1);
        System.out.println(schiNotesService.consultarUsuarioPorCorreo("camer.cl98@gmail.com"));
        */
    }
    
    

}
