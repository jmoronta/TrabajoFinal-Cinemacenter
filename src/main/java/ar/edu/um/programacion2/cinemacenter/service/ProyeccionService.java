package ar.edu.um.programacion2.cinemacenter.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.um.programacion2.cinemacenter.domain.Proyeccion;
import ar.edu.um.programacion2.cinemacenter.repository.*;

@Service
public class ProyeccionService {
	
	private ProyeccionRepository proyeccionRepository;
	
	@Autowired
	public ProyeccionService(ProyeccionRepository proyeccionRepository) {
		this.proyeccionRepository = proyeccionRepository;
	}

	public List<Proyeccion> buscarPorFecha(LocalDate inicio, LocalDate fin) {
        List<Proyeccion> proyecciones = proyeccionRepository.findAllByFechaComienzoBetween(inicio,fin);
        return proyecciones;
    }
	
}
