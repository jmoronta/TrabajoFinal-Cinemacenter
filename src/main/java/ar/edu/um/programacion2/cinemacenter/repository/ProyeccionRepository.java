package ar.edu.um.programacion2.cinemacenter.repository;

import ar.edu.um.programacion2.cinemacenter.domain.Pelicula;
import ar.edu.um.programacion2.cinemacenter.domain.Proyeccion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Proyeccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long> {
	public List<Proyeccion> findAllByFechaComienzoBetween(LocalDate inicio, LocalDate fin);
}
