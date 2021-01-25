package ar.edu.um.programacion2.cinemacenter.web.rest;

import ar.edu.um.programacion2.cinemacenter.domain.Butaca;
import ar.edu.um.programacion2.cinemacenter.repository.ButacaRepository;
import ar.edu.um.programacion2.cinemacenter.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.edu.um.programacion2.cinemacenter.domain.Butaca}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ButacaResource {

    private final Logger log = LoggerFactory.getLogger(ButacaResource.class);

    private static final String ENTITY_NAME = "butaca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ButacaRepository butacaRepository;

    public ButacaResource(ButacaRepository butacaRepository) {
        this.butacaRepository = butacaRepository;
    }

    /**
     * {@code POST  /butacas} : Create a new butaca.
     *
     * @param butaca the butaca to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new butaca, or with status {@code 400 (Bad Request)} if the butaca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/butacas")
    public ResponseEntity<Butaca> createButaca(@RequestBody Butaca butaca) throws URISyntaxException {
        log.debug("REST request to save Butaca : {}", butaca);
        if (butaca.getId() != null) {
            throw new BadRequestAlertException("A new butaca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Butaca result = butacaRepository.save(butaca);
        return ResponseEntity.created(new URI("/api/butacas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /butacas} : Updates an existing butaca.
     *
     * @param butaca the butaca to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated butaca,
     * or with status {@code 400 (Bad Request)} if the butaca is not valid,
     * or with status {@code 500 (Internal Server Error)} if the butaca couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/butacas")
    public ResponseEntity<Butaca> updateButaca(@RequestBody Butaca butaca) throws URISyntaxException {
        log.debug("REST request to update Butaca : {}", butaca);
        if (butaca.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Butaca result = butacaRepository.save(butaca);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, butaca.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /butacas} : get all the butacas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of butacas in body.
     */
    @GetMapping("/butacas")
    public List<Butaca> getAllButacas() {
        log.debug("REST request to get all Butacas");
        return butacaRepository.findAll();
    }

    /**
     * {@code GET  /butacas/:id} : get the "id" butaca.
     *
     * @param id the id of the butaca to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the butaca, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/butacas/{id}")
    public ResponseEntity<Butaca> getButaca(@PathVariable Long id) {
        log.debug("REST request to get Butaca : {}", id);
        Optional<Butaca> butaca = butacaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(butaca);
    }

    /**
     * {@code DELETE  /butacas/:id} : delete the "id" butaca.
     *
     * @param id the id of the butaca to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/butacas/{id}")
    public ResponseEntity<Void> deleteButaca(@PathVariable Long id) {
        log.debug("REST request to delete Butaca : {}", id);
        butacaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
