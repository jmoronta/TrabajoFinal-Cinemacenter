package ar.edu.um.programacion2.cinemacenter.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ar.edu.um.programacion2.cinemacenter.domain.Pelicula;
import ar.edu.um.programacion2.cinemacenter.domain.Proyeccion;
import ar.edu.um.programacion2.cinemacenter.domain.Venta;
import ar.edu.um.programacion2.cinemacenter.repository.VentaRepository;
import ar.edu.um.programacion2.cinemacenter.web.rest.VentaResource;

@Service
public class VentaService {
	
	@Autowired
	private VentaRepository ventarepository;
	
	
	public VentaService(VentaRepository ventarepository) {
		super();
		this.ventarepository = ventarepository;
	}

	public List<Venta> buscarPorFecha(LocalDate inicio, LocalDate fin) {
        List<Venta> ventasporfecha = ventarepository.findAllByFechaVentaBetween(inicio,fin);
        return ventasporfecha;
    }

	public List<Venta> findByProyeccion(Long proyeccionid) {
		List<Venta>ventasproyeccion = ventarepository.findAllByProyeccion(proyeccionid);
		return ventasproyeccion;
	}
	
	public List<CantidadProyeccion> getRankingProyeccion(LocalDate inicio, LocalDate fin) {
    	Map<Proyeccion,CantidadProyeccion>mapacantidadporproyeccion = new HashMap();
    	List<CantidadProyeccion>proyeccionesmasvendidas = new ArrayList<>();
    	List<Venta> todaslasventas = new ArrayList<Venta>();
        todaslasventas = this.buscarPorFecha(inicio, fin);
        CantidadProyeccion auxcantidad;
        for (Venta aux: todaslasventas) {
        	if(aux.getProyeccion() != null) {
	        	auxcantidad = mapacantidadporproyeccion.get(aux.getProyeccion());
	        	if (auxcantidad == null) {
	        		mapacantidadporproyeccion.put(aux.getProyeccion(), new CantidadProyeccion(aux.getProyeccion()));
	        	} 
	        	else 
	        		auxcantidad.sumarcantidad();
        	}
        }
        List<CantidadProyeccion> listaordenada = new ArrayList<CantidadProyeccion>(mapacantidadporproyeccion.values());
        Collections.sort(listaordenada);
        try {
        	
	        for (int i=0;i< 5; i++ ) {
	        	proyeccionesmasvendidas.add(listaordenada.get(i));
	        }
        }    
	    catch (IndexOutOfBoundsException e) {
			//hay menos de 5 proyecciones
		}
	        return proyeccionesmasvendidas;
    }

	public List<CantidadVentasPelicula> cantidadporpelicula(List<Pelicula> peliculas) {
		List<CantidadVentasPelicula>ventasporpelicula = new ArrayList<CantidadVentasPelicula>();
    	List<Venta> todaslasventas = new ArrayList<Venta>();
    	for(Pelicula peliculaaux: peliculas) {
    		todaslasventas = ventarepository.findAllByPelicula(peliculaaux);
    		ventasporpelicula.add(new CantidadVentasPelicula(peliculaaux,todaslasventas.size()));
    	}
    	Collections.sort(ventasporpelicula);
		return ventasporpelicula;
	}
	
}
