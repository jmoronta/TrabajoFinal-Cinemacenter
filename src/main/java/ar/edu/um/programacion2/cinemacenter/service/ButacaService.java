package ar.edu.um.programacion2.cinemacenter.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.programacion2.cinemacenter.domain.Butaca;
import ar.edu.um.programacion2.cinemacenter.repository.ButacaRepository;


@Service
public class ButacaService {
	
	@Autowired
	private ButacaRepository butacarepository;
	
	public List<Butaca> findByEstado(Boolean estado) {
		List<Butaca> butacas = butacarepository.findByEstado(estado);
		return butacas;
	}

}
