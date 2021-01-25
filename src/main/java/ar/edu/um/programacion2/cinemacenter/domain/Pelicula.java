package ar.edu.um.programacion2.cinemacenter.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pelicula.
 */
@Entity
@Table(name = "pelicula")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pelicula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "genero")
    private String genero;

    @Column(name = "clasifiacion")
    private String clasifiacion;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "fecha_comienzo")
    private LocalDate fechaComienzo;

    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    @OneToMany(mappedBy = "pelicula")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Proyeccion> proyeccions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Pelicula nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public Pelicula duracion(Integer duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Pelicula descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public Pelicula detalle(String detalle) {
        this.detalle = detalle;
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getGenero() {
        return genero;
    }

    public Pelicula genero(String genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getClasifiacion() {
        return clasifiacion;
    }

    public Pelicula clasifiacion(String clasifiacion) {
        this.clasifiacion = clasifiacion;
        return this;
    }

    public void setClasifiacion(String clasifiacion) {
        this.clasifiacion = clasifiacion;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Pelicula estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public LocalDate getFechaComienzo() {
        return fechaComienzo;
    }

    public Pelicula fechaComienzo(LocalDate fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
        return this;
    }

    public void setFechaComienzo(LocalDate fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public Pelicula fechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
        return this;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Set<Proyeccion> getProyeccions() {
        return proyeccions;
    }

    public Pelicula proyeccions(Set<Proyeccion> proyeccions) {
        this.proyeccions = proyeccions;
        return this;
    }

    public Pelicula addProyeccion(Proyeccion proyeccion) {
        this.proyeccions.add(proyeccion);
        proyeccion.setPelicula(this);
        return this;
    }

    public Pelicula removeProyeccion(Proyeccion proyeccion) {
        this.proyeccions.remove(proyeccion);
        proyeccion.setPelicula(null);
        return this;
    }

    public void setProyeccions(Set<Proyeccion> proyeccions) {
        this.proyeccions = proyeccions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pelicula)) {
            return false;
        }
        return id != null && id.equals(((Pelicula) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pelicula{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", duracion=" + getDuracion() +
            ", descripcion='" + getDescripcion() + "'" +
            ", detalle='" + getDetalle() + "'" +
            ", genero='" + getGenero() + "'" +
            ", clasifiacion='" + getClasifiacion() + "'" +
            ", estado='" + isEstado() + "'" +
            ", fechaComienzo='" + getFechaComienzo() + "'" +
            ", fechaFinalizacion='" + getFechaFinalizacion() + "'" +
            "}";
    }
}
