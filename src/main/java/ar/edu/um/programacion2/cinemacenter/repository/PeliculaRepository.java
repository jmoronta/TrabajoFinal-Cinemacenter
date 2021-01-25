package ar.edu.um.programacion2.cinemacenter.repository;

import ar.edu.um.programacion2.cinemacenter.domain.Pelicula;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pelicula entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

	public Optional<Pelicula> findAllByNombreLike(String nombre);
    public List<Pelicula> findAllByFechaComienzoBetween(LocalDate inicio, LocalDate fin);
}
