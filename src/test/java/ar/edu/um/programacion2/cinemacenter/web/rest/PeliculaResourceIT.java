package ar.edu.um.programacion2.cinemacenter.web.rest;

import ar.edu.um.programacion2.cinemacenter.CinemacenterApp;
import ar.edu.um.programacion2.cinemacenter.domain.Pelicula;
import ar.edu.um.programacion2.cinemacenter.repository.PeliculaRepository;

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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PeliculaResource} REST controller.
 */
@SpringBootTest(classes = CinemacenterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PeliculaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURACION = 1;
    private static final Integer UPDATED_DURACION = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_DETALLE = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final String DEFAULT_CLASIFIACION = "AAAAAAAAAA";
    private static final String UPDATED_CLASIFIACION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final LocalDate DEFAULT_FECHA_COMIENZO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_COMIENZO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FINALIZACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FINALIZACION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeliculaMockMvc;

    private Pelicula pelicula;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pelicula createEntity(EntityManager em) {
        Pelicula pelicula = new Pelicula()
            .nombre(DEFAULT_NOMBRE)
            .duracion(DEFAULT_DURACION)
            .descripcion(DEFAULT_DESCRIPCION)
            .detalle(DEFAULT_DETALLE)
            .genero(DEFAULT_GENERO)
            .clasifiacion(DEFAULT_CLASIFIACION)
            .estado(DEFAULT_ESTADO)
            .fechaComienzo(DEFAULT_FECHA_COMIENZO)
            .fechaFinalizacion(DEFAULT_FECHA_FINALIZACION);
        return pelicula;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pelicula createUpdatedEntity(EntityManager em) {
        Pelicula pelicula = new Pelicula()
            .nombre(UPDATED_NOMBRE)
            .duracion(UPDATED_DURACION)
            .descripcion(UPDATED_DESCRIPCION)
            .detalle(UPDATED_DETALLE)
            .genero(UPDATED_GENERO)
            .clasifiacion(UPDATED_CLASIFIACION)
            .estado(UPDATED_ESTADO)
            .fechaComienzo(UPDATED_FECHA_COMIENZO)
            .fechaFinalizacion(UPDATED_FECHA_FINALIZACION);
        return pelicula;
    }

    @BeforeEach
    public void initTest() {
        pelicula = createEntity(em);
    }

    @Test
    @Transactional
    public void createPelicula() throws Exception {
        int databaseSizeBeforeCreate = peliculaRepository.findAll().size();
        // Create the Pelicula
        restPeliculaMockMvc.perform(post("/api/peliculas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pelicula)))
            .andExpect(status().isCreated());

        // Validate the Pelicula in the database
        List<Pelicula> peliculaList = peliculaRepository.findAll();
        assertThat(peliculaList).hasSize(databaseSizeBeforeCreate + 1);
        Pelicula testPelicula = peliculaList.get(peliculaList.size() - 1);
        assertThat(testPelicula.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPelicula.getDuracion()).isEqualTo(DEFAULT_DURACION);
        assertThat(testPelicula.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPelicula.getDetalle()).isEqualTo(DEFAULT_DETALLE);
        assertThat(testPelicula.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testPelicula.getClasifiacion()).isEqualTo(DEFAULT_CLASIFIACION);
        assertThat(testPelicula.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testPelicula.getFechaComienzo()).isEqualTo(DEFAULT_FECHA_COMIENZO);
        assertThat(testPelicula.getFechaFinalizacion()).isEqualTo(DEFAULT_FECHA_FINALIZACION);
    }

    @Test
    @Transactional
    public void createPeliculaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peliculaRepository.findAll().size();

        // Create the Pelicula with an existing ID
        pelicula.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeliculaMockMvc.perform(post("/api/peliculas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pelicula)))
            .andExpect(status().isBadRequest());

        // Validate the Pelicula in the database
        List<Pelicula> peliculaList = peliculaRepository.findAll();
        assertThat(peliculaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPeliculas() throws Exception {
        // Initialize the database
        peliculaRepository.saveAndFlush(pelicula);

        // Get all the peliculaList
        restPeliculaMockMvc.perform(get("/api/peliculas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pelicula.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].detalle").value(hasItem(DEFAULT_DETALLE)))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO)))
            .andExpect(jsonPath("$.[*].clasifiacion").value(hasItem(DEFAULT_CLASIFIACION)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaComienzo").value(hasItem(DEFAULT_FECHA_COMIENZO.toString())))
            .andExpect(jsonPath("$.[*].fechaFinalizacion").value(hasItem(DEFAULT_FECHA_FINALIZACION.toString())));
    }
    
    @Test
    @Transactional
    public void getPelicula() throws Exception {
        // Initialize the database
        peliculaRepository.saveAndFlush(pelicula);

        // Get the pelicula
        restPeliculaMockMvc.perform(get("/api/peliculas/{id}", pelicula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pelicula.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.detalle").value(DEFAULT_DETALLE))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO))
            .andExpect(jsonPath("$.clasifiacion").value(DEFAULT_CLASIFIACION))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.fechaComienzo").value(DEFAULT_FECHA_COMIENZO.toString()))
            .andExpect(jsonPath("$.fechaFinalizacion").value(DEFAULT_FECHA_FINALIZACION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPelicula() throws Exception {
        // Get the pelicula
        restPeliculaMockMvc.perform(get("/api/peliculas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePelicula() throws Exception {
        // Initialize the database
        peliculaRepository.saveAndFlush(pelicula);

        int databaseSizeBeforeUpdate = peliculaRepository.findAll().size();

        // Update the pelicula
        Pelicula updatedPelicula = peliculaRepository.findById(pelicula.getId()).get();
        // Disconnect from session so that the updates on updatedPelicula are not directly saved in db
        em.detach(updatedPelicula);
        updatedPelicula
            .nombre(UPDATED_NOMBRE)
            .duracion(UPDATED_DURACION)
            .descripcion(UPDATED_DESCRIPCION)
            .detalle(UPDATED_DETALLE)
            .genero(UPDATED_GENERO)
            .clasifiacion(UPDATED_CLASIFIACION)
            .estado(UPDATED_ESTADO)
            .fechaComienzo(UPDATED_FECHA_COMIENZO)
            .fechaFinalizacion(UPDATED_FECHA_FINALIZACION);

        restPeliculaMockMvc.perform(put("/api/peliculas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPelicula)))
            .andExpect(status().isOk());

        // Validate the Pelicula in the database
        List<Pelicula> peliculaList = peliculaRepository.findAll();
        assertThat(peliculaList).hasSize(databaseSizeBeforeUpdate);
        Pelicula testPelicula = peliculaList.get(peliculaList.size() - 1);
        assertThat(testPelicula.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPelicula.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testPelicula.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPelicula.getDetalle()).isEqualTo(UPDATED_DETALLE);
        assertThat(testPelicula.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testPelicula.getClasifiacion()).isEqualTo(UPDATED_CLASIFIACION);
        assertThat(testPelicula.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testPelicula.getFechaComienzo()).isEqualTo(UPDATED_FECHA_COMIENZO);
        assertThat(testPelicula.getFechaFinalizacion()).isEqualTo(UPDATED_FECHA_FINALIZACION);
    }

    @Test
    @Transactional
    public void updateNonExistingPelicula() throws Exception {
        int databaseSizeBeforeUpdate = peliculaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeliculaMockMvc.perform(put("/api/peliculas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pelicula)))
            .andExpect(status().isBadRequest());

        // Validate the Pelicula in the database
        List<Pelicula> peliculaList = peliculaRepository.findAll();
        assertThat(peliculaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePelicula() throws Exception {
        // Initialize the database
        peliculaRepository.saveAndFlush(pelicula);

        int databaseSizeBeforeDelete = peliculaRepository.findAll().size();

        // Delete the pelicula
        restPeliculaMockMvc.perform(delete("/api/peliculas/{id}", pelicula.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pelicula> peliculaList = peliculaRepository.findAll();
        assertThat(peliculaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
