package edu.eci.arsw.schinotes;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author carlo
 */
@SpringBootApplication
@ComponentScan(basePackages = "edu.eci.arsw")
public class SchiNotesApplication {

    @Autowired
    DataSource dataSource;
    public static void main(String[] args) {
        SpringApplication.run(SchiNotesApplication.class, args);
    }

}
