package edu.eci.arsw.SchiNotes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.SchiNotes.model.Horario;
import edu.eci.arsw.SchiNotes.persistence.HorarioRepository;

@Service
/**
 * HorarioServiceImpl
 */
public class SchiNotesServiceImpl implements SchiNotesService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<Horario> getHorarios() {
        return horarioRepository.findAll();
    }
    
    
}