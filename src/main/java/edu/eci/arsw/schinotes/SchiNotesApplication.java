package edu.eci.arsw.schinotes;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author carlo
 */
@SpringBootApplication
@ComponentScan(value = "edu.eci.arsw.schinotes")
public class SchiNotesApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SchiNotesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Connection Polling datasource : "+ dataSource);  // check connection pooling
        System.out.println("Connection : "+ dataSource.getConnection());  // check connection
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
