package ar.edu.um.programacion2.cinemacenter.repository;

import ar.edu.um.programacion2.cinemacenter.domain.Sala;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Sala entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    public Optional<Sala> findAllByNombreLike(String nombre);
}
