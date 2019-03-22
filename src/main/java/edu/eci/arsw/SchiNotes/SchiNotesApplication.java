package edu.eci.arsw.SchiNotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.SchiNotes"})
public class SchiNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchiNotesApplication.class, args);
	}

}
