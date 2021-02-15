package ar.edu.um.programacion2.cinemacenter.web.rest;

import ar.edu.um.programacion2.cinemacenter.domain.Butaca;
import ar.edu.um.programacion2.cinemacenter.domain.Pelicula;
import ar.edu.um.programacion2.cinemacenter.domain.Proyeccion;
import ar.edu.um.programacion2.cinemacenter.domain.Venta;
import ar.edu.um.programacion2.cinemacenter.repository.ButacaRepository;
import ar.edu.um.programacion2.cinemacenter.repository.ProyeccionRepository;
import ar.edu.um.programacion2.cinemacenter.repository.VentaRepository;
import ar.edu.um.programacion2.cinemacenter.service.ButacaService;
import ar.edu.um.programacion2.cinemacenter.service.CantidadProyeccion;
import ar.edu.um.programacion2.cinemacenter.service.CantidadVentasPelicula;
import ar.edu.um.programacion2.cinemacenter.service.PeliculaService;
import ar.edu.um.programacion2.cinemacenter.service.VentaService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.edu.um.programacion2.cinemacenter.domain.Venta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VentaResource {

    private final Logger log = LoggerFactory.getLogger(VentaResource.class);

    private static final String ENTITY_NAME = "venta";
    
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
    private final VentaRepository ventaRepository;
    
    @Autowired
    private  VentaService ventaservice;

    @Autowired
    private ButacaRepository butacarepository;
    
    @Autowired
    private ProyeccionRepository proyeccionrepository;
    
    @Autowired
    private PeliculaService peliculaservice;
    

    public VentaResource(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }
    /**
     * {@code POST  /ventas} : Create a new venta.
     *
     * @param venta the venta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new venta, or with status {@code 400 (Bad Request)} if the venta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ventas")
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta) throws URISyntaxException {
        log.debug("REST request to save Venta : {}", venta);
        if (venta.getId() != null) {
            throw new BadRequestAlertException("A new venta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //Proyeccion
        Optional<Proyeccion>proyeccion =  proyeccionrepository.findById(venta.getProyeccion().getId());
        try {
        	if (proyeccion.get().getFechaComienzo().isAfter(LocalDate.now())) {
            	throw new BadRequestAlertException("La proyeccion todavia no esta disponible", ENTITY_NAME, "idexists");
            }
        	if (proyeccion.get().getFechaFinalizacion().isBefore(LocalDate.now())) {
            	throw new BadRequestAlertException("La proyeccion ya no esta disponible", ENTITY_NAME, "idexists");
            }
            venta.setProyeccion(proyeccion.get());
        }catch(NoSuchElementException ex) {
        	throw new BadRequestAlertException("NO existe la proyeccion", ENTITY_NAME, "idexists");
        	}
        
        //Butaca
        Optional<Butaca>butaca =  butacarepository.findById(venta.getButaca().getId());
        try {
        	if (butaca.get().isEstado()) {
            	throw new BadRequestAlertException("La butaca ya se encuentra vendida", ENTITY_NAME, "idexists");
            }
            butaca.get().setEstado(true);
            butaca.get().setProyeccion(proyeccion.get());
            butaca.get().setFechaVenta(LocalDate.now());
            butacarepository.save(butaca.get());
            venta.setButaca(butaca.get());
        }catch(NoSuchElementException ex) {
        	throw new BadRequestAlertException("NO existe butaca", ENTITY_NAME, "idexists");
        	}
       
        Venta result = ventaRepository.save(venta);
        return ResponseEntity.created(new URI("/api/ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ventas} : Updates an existing venta.
     *
     * @param venta the venta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venta,
     * or with status {@code 400 (Bad Request)} if the venta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the venta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ventas")
    public ResponseEntity<Venta> updateVenta(@RequestBody Venta venta) throws URISyntaxException {
        log.debug("REST request to update Venta : {}", venta);
        if (venta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Venta result = ventaRepository.save(venta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, venta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ventas} : get all the ventas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventas in body.
     */
    @GetMapping("/ventas")
    public List<Venta> getAllVentas() {
        log.debug("REST request to get all Ventas");
        return ventaRepository.findAll();
    }
    
       
    @GetMapping("/ventas/reportes/porango/{inicio}/{fin}")
    public List<Venta> getAllVentasporrango(@PathVariable LocalDate inicio,@PathVariable LocalDate fin) {
        log.debug("REST request to get all Ventas: {0} {1}", inicio,fin);
        List<Venta> todaslasventas = new ArrayList<Venta>();
        todaslasventas = ventaservice.buscarPorFecha(inicio, fin);
        System.out.print("ESTAS SON TODAS LAS PROYECCIONES:"+todaslasventas);
                
        return todaslasventas;
    }
    
    @GetMapping("/ventas/reportes/porangoyproyeccion/{proyeccion}/{inicio}/{fin}")
    public List<Venta> getAllVentasporrango(@PathVariable Long proyeccion,@PathVariable LocalDate inicio,@PathVariable LocalDate fin) {
        log.debug("REST request to get all Ventas: {0} {1} {2}",proyeccion,inicio,fin);
        List<Venta> todaslasventas = new ArrayList<Venta>();
        List<Venta> ventasporproyeccion = new ArrayList<Venta>();
        todaslasventas = ventaservice.buscarPorFecha(inicio, fin);
        for (int i = 0; i < todaslasventas.size(); i++) {
        	try {
    			if(todaslasventas.get(i).getProyeccion().getId().equals(proyeccion))	{
    				ventasporproyeccion.add(todaslasventas.get(i));
       	            }
    		}catch(NullPointerException ex) {
            	//throw new BadRequestAlertException("NO existe Proyeccion asocicada a esa venta", ENTITY_NAME, "idexists");
            //No hacemos nada en esta captura de excepcion porque queremos evitar que las ventas sin proyeccion rompan el bucle.	
    		}
        }
                
        return ventasporproyeccion;
    }

    /**
     * {@code GET  /ventas/:id} : get the "id" venta.
     *
     * @param id the id of the venta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the venta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ventas/{id}")
    public ResponseEntity<Venta> getVenta(@PathVariable Long id) {
        log.debug("REST request to get Venta : {}", id);
        Optional<Venta> venta = ventaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(venta);
    }
    
    @GetMapping("/ventas/rankingproyeccion/{inicio}/{fin}")
    public List<CantidadProyeccion> getRankingProyeccion(@PathVariable LocalDate inicio,@PathVariable LocalDate fin) {
    	log.debug("REST request to get all Ventas: {0} {1}", inicio,fin);
    	
    	return ventaservice.getRankingProyeccion(inicio,fin);
    }
    @GetMapping("/ventas/porpelicula")
    public List<CantidadVentasPelicula> ventaporpelicula() {
        //log.debug("REST request to get Venta : {}", id);
    	List<Pelicula> peliculas = peliculaservice.buscarPorEstado(true);
    	return ventaservice.cantidadporpelicula(peliculas);
    }

    /**
     * {@code DELETE  /ventas/:id} : delete the "id" venta.
     *
     * @param id the id of the venta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ventas/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        log.debug("REST request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
