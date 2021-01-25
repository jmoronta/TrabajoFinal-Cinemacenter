package ar.edu.um.programacion2.cinemacenter.service;


import ar.edu.um.programacion2.cinemacenter.domain.Sala;
import ar.edu.um.programacion2.cinemacenter.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaService {
    private SalaRepository salaRepository;

    @Autowired
    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }
    public Optional<Sala> buscarPorNombre(String nombre) {
        Optional<Sala> sala = salaRepository.findAllByNombreLike(nombre);
        return sala;
    }

}
