package ar.edu.um.programacion2.cinemacenter.web.rest;

import ar.edu.um.programacion2.cinemacenter.CinemacenterApp;
import ar.edu.um.programacion2.cinemacenter.domain.Proyeccion;
import ar.edu.um.programacion2.cinemacenter.repository.ProyeccionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ar.edu.um.programacion2.cinemacenter.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProyeccionResource} REST controller.
 */
@SpringBootTest(classes = CinemacenterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProyeccionResourceIT {

    private static final ZonedDateTime DEFAULT_HORA_PROYECCION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_PROYECCION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final LocalDate DEFAULT_FECHA_COMIENZO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_COMIENZO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FINALIZACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FINALIZACION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProyeccionRepository proyeccionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProyeccionMockMvc;

    private Proyeccion proyeccion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyeccion createEntity(EntityManager em) {
        Proyeccion proyeccion = new Proyeccion()
            .horaProyeccion(DEFAULT_HORA_PROYECCION)
            .estado(DEFAULT_ESTADO)
            .fechaComienzo(DEFAULT_FECHA_COMIENZO)
            .fechaFinalizacion(DEFAULT_FECHA_FINALIZACION);
        return proyeccion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyeccion createUpdatedEntity(EntityManager em) {
        Proyeccion proyeccion = new Proyeccion()
            .horaProyeccion(UPDATED_HORA_PROYECCION)
            .estado(UPDATED_ESTADO)
            .fechaComienzo(UPDATED_FECHA_COMIENZO)
            .fechaFinalizacion(UPDATED_FECHA_FINALIZACION);
        return proyeccion;
    }

    @BeforeEach
    public void initTest() {
        proyeccion = createEntity(em);
    }

    @Test
    @Transactional
    public void createProyeccion() throws Exception {
        int databaseSizeBeforeCreate = proyeccionRepository.findAll().size();
        // Create the Proyeccion
        restProyeccionMockMvc.perform(post("/api/proyeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyeccion)))
            .andExpect(status().isCreated());

        // Validate the Proyeccion in the database
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeCreate + 1);
        Proyeccion testProyeccion = proyeccionList.get(proyeccionList.size() - 1);
        assertThat(testProyeccion.getHoraProyeccion()).isEqualTo(DEFAULT_HORA_PROYECCION);
        assertThat(testProyeccion.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProyeccion.getFechaComienzo()).isEqualTo(DEFAULT_FECHA_COMIENZO);
        assertThat(testProyeccion.getFechaFinalizacion()).isEqualTo(DEFAULT_FECHA_FINALIZACION);
    }

    @Test
    @Transactional
    public void createProyeccionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proyeccionRepository.findAll().size();

        // Create the Proyeccion with an existing ID
        proyeccion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProyeccionMockMvc.perform(post("/api/proyeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyeccion)))
            .andExpect(status().isBadRequest());

        // Validate the Proyeccion in the database
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProyeccions() throws Exception {
        // Initialize the database
        proyeccionRepository.saveAndFlush(proyeccion);

        // Get all the proyeccionList
        restProyeccionMockMvc.perform(get("/api/proyeccions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proyeccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaProyeccion").value(hasItem(sameInstant(DEFAULT_HORA_PROYECCION))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaComienzo").value(hasItem(DEFAULT_FECHA_COMIENZO.toString())))
            .andExpect(jsonPath("$.[*].fechaFinalizacion").value(hasItem(DEFAULT_FECHA_FINALIZACION.toString())));
    }
    
    @Test
    @Transactional
    public void getProyeccion() throws Exception {
        // Initialize the database
        proyeccionRepository.saveAndFlush(proyeccion);

        // Get the proyeccion
        restProyeccionMockMvc.perform(get("/api/proyeccions/{id}", proyeccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proyeccion.getId().intValue()))
            .andExpect(jsonPath("$.horaProyeccion").value(sameInstant(DEFAULT_HORA_PROYECCION)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.fechaComienzo").value(DEFAULT_FECHA_COMIENZO.toString()))
            .andExpect(jsonPath("$.fechaFinalizacion").value(DEFAULT_FECHA_FINALIZACION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProyeccion() throws Exception {
        // Get the proyeccion
        restProyeccionMockMvc.perform(get("/api/proyeccions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProyeccion() throws Exception {
        // Initialize the database
        proyeccionRepository.saveAndFlush(proyeccion);

        int databaseSizeBeforeUpdate = proyeccionRepository.findAll().size();

        // Update the proyeccion
        Proyeccion updatedProyeccion = proyeccionRepository.findById(proyeccion.getId()).get();
        // Disconnect from session so that the updates on updatedProyeccion are not directly saved in db
        em.detach(updatedProyeccion);
        updatedProyeccion
            .horaProyeccion(UPDATED_HORA_PROYECCION)
            .estado(UPDATED_ESTADO)
            .fechaComienzo(UPDATED_FECHA_COMIENZO)
            .fechaFinalizacion(UPDATED_FECHA_FINALIZACION);

        restProyeccionMockMvc.perform(put("/api/proyeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProyeccion)))
            .andExpect(status().isOk());

        // Validate the Proyeccion in the database
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeUpdate);
        Proyeccion testProyeccion = proyeccionList.get(proyeccionList.size() - 1);
        assertThat(testProyeccion.getHoraProyeccion()).isEqualTo(UPDATED_HORA_PROYECCION);
        assertThat(testProyeccion.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProyeccion.getFechaComienzo()).isEqualTo(UPDATED_FECHA_COMIENZO);
        assertThat(testProyeccion.getFechaFinalizacion()).isEqualTo(UPDATED_FECHA_FINALIZACION);
    }

    @Test
    @Transactional
    public void updateNonExistingProyeccion() throws Exception {
        int databaseSizeBeforeUpdate = proyeccionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProyeccionMockMvc.perform(put("/api/proyeccions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyeccion)))
            .andExpect(status().isBadRequest());

        // Validate the Proyeccion in the database
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProyeccion() throws Exception {
        // Initialize the database
        proyeccionRepository.saveAndFlush(proyeccion);

        int databaseSizeBeforeDelete = proyeccionRepository.findAll().size();

        // Delete the proyeccion
        restProyeccionMockMvc.perform(delete("/api/proyeccions/{id}", proyeccion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proyeccion> proyeccionList = proyeccionRepository.findAll();
        assertThat(proyeccionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
