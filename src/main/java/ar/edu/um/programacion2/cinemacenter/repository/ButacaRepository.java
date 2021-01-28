package ar.edu.um.programacion2.cinemacenter.repository;

import ar.edu.um.programacion2.cinemacenter.domain.Butaca;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Butaca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ButacaRepository extends JpaRepository<Butaca, Long> {

	public List<Butaca> findByEstado(Boolean estado);
}
