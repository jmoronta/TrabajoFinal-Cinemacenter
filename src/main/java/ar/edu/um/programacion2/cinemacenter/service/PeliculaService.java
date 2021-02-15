package ar.edu.um.programacion2.cinemacenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.um.programacion2.cinemacenter.domain.*;
import ar.edu.um.programacion2.cinemacenter.repository.PeliculaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

	private PeliculaRepository peliculaRepository;

    @Autowired
	public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

	public Optional<Pelicula> buscarPorNombre(String nombre) {
		Optional<Pelicula> pelicula = peliculaRepository.findAllByNombreLike(nombre);
		return pelicula;
	}

    public List<Pelicula> buscarPorFecha(LocalDate inicio, LocalDate fin) {
        List<Pelicula> peliculas = peliculaRepository.findAllByFechaComienzoBetween(inicio,fin);
        return peliculas;
    }
    public List<Pelicula> buscarPorEstado(Boolean estado) {
        List<Pelicula> peliculas = peliculaRepository.findAllByEstado(estado);
        return peliculas;
    }
}
