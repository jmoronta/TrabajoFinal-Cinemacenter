package ar.edu.um.programacion2.cinemacenter.web.rest;

import ar.edu.um.programacion2.cinemacenter.domain.Sala;
import ar.edu.um.programacion2.cinemacenter.repository.SalaRepository;
import ar.edu.um.programacion2.cinemacenter.service.SalaService;
import ar.edu.um.programacion2.cinemacenter.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing {@link ar.edu.um.programacion2.cinemacenter.domain.Sala}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SalaResource {

    private final Logger log = LoggerFactory.getLogger(SalaResource.class);

    private static final String ENTITY_NAME = "sala";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalaRepository salaRepository;
    @Autowired
    private SalaService salaService;

    public SalaResource(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;

    }

    /**
     * {@code POST  /salas} : Create a new sala.
     *
     * @param sala the sala to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sala, or with status {@code 400 (Bad Request)} if the sala has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salas")
    public ResponseEntity<Sala> createSala(@RequestBody Sala sala) throws URISyntaxException {
        log.debug("REST request to save Sala : {}", sala);
        if (sala.getId() != null) {
            throw new BadRequestAlertException("A new sala cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sala result = salaRepository.save(sala);
        return ResponseEntity.created(new URI("/api/salas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salas} : Updates an existing sala.
     *
     * @param sala the sala to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sala,
     * or with status {@code 400 (Bad Request)} if the sala is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sala couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salas")
    public ResponseEntity<Sala> updateSala(@RequestBody Sala sala) throws URISyntaxException {
        log.debug("REST request to update Sala : {}", sala);
        if (sala.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sala result = salaRepository.save(sala);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sala.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /salas} : get all the salas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salas in body.
     */
    @GetMapping("/salas")
    public List<Sala> getAllSalas() {
        log.debug("REST request to get all Salas");
        return salaRepository.findAll();
    }

    /**
     * {@code GET  /salas/:id} : get the "id" sala.
     *
     * @param id the id of the sala to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sala, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salas/{id}")
    public ResponseEntity<Sala> getSala(@PathVariable Long id) {
        log.debug("REST request to get Sala : {}", id);
        Optional<Sala> sala = salaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sala);
    }
    @GetMapping("/salas/nombre/{nombre}")
    public ResponseEntity<Sala> getSala(@PathVariable String nombre) {
        log.debug("REST request to get Sala : {}", nombre);
        Optional<Sala> sala = salaService.buscarPorNombre(nombre);
        return ResponseUtil.wrapOrNotFound(sala);
    }
    /**
     * {@code DELETE  /salas/:id} : delete the "id" sala.
     *
     * @param id the id of the sala to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salas/{id}")
    public ResponseEntity<String> deleteSala(@PathVariable Long id) {
        log.debug("REST request to delete Sala : {}", id);
        Optional<Sala> sala = salaRepository.findById(id);
        if (sala.get().getEstado().equals(1)) {
            salaRepository.deleteById(id);
            return new ResponseEntity<String>("Se borro satisfactoriamente la sala", HttpStatus.OK);
            //return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        }
        else 
        	return new ResponseEntity<String>("No see puede borrar una sala habilitada", HttpStatus.OK);
     }
}
