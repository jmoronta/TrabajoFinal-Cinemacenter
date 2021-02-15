package ar.edu.um.programacion2.cinemacenter.repository;

import ar.edu.um.programacion2.cinemacenter.domain.Pelicula;
import ar.edu.um.programacion2.cinemacenter.domain.Venta;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Venta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

	public List<Venta> findAllByFechaVentaBetween(LocalDate inicio, LocalDate fin);

	public List<Venta> findAllByProyeccion(Long proyeccionid);
	
	//public List<Venta> findAllByPelicula(Long peliculaid);
	
	public List<Venta> findAllByPelicula(Pelicula pelicula);

}
