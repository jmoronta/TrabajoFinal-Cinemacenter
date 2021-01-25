package ar.edu.um.programacion2.cinemacenter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Proyeccion.
 */
@Entity
@Table(name = "proyeccion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Proyeccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hora_proyeccion")
    private ZonedDateTime horaProyeccion;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "fecha_comienzo")
    private LocalDate fechaComienzo;

    @Column(name = "fecha_finalizacion")
    private LocalDate fechaFinalizacion;

    @OneToMany(mappedBy = "proyeccion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Butaca> butacas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "proyeccions", allowSetters = true)
    private Pelicula pelicula;

    @ManyToOne
    @JsonIgnoreProperties(value = "proyeccions", allowSetters = true)
    private Sala sala;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getHoraProyeccion() {
        return horaProyeccion;
    }

    public Proyeccion horaProyeccion(ZonedDateTime horaProyeccion) {
        this.horaProyeccion = horaProyeccion;
        return this;
    }

    public void setHoraProyeccion(ZonedDateTime horaProyeccion) {
        this.horaProyeccion = horaProyeccion;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Proyeccion estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public LocalDate getFechaComienzo() {
        return fechaComienzo;
    }

    public Proyeccion fechaComienzo(LocalDate fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
        return this;
    }

    public void setFechaComienzo(LocalDate fechaComienzo) {
        this.fechaComienzo = fechaComienzo;
    }

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public Proyeccion fechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
        return this;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Set<Butaca> getButacas() {
        return butacas;
    }

    public Proyeccion butacas(Set<Butaca> butacas) {
        this.butacas = butacas;
        return this;
    }

    public Proyeccion addButaca(Butaca butaca) {
        this.butacas.add(butaca);
        butaca.setProyeccion(this);
        return this;
    }

    public Proyeccion removeButaca(Butaca butaca) {
        this.butacas.remove(butaca);
        butaca.setProyeccion(null);
        return this;
    }

    public void setButacas(Set<Butaca> butacas) {
        this.butacas = butacas;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Proyeccion pelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        return this;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public Proyeccion sala(Sala sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proyeccion)) {
            return false;
        }
        return id != null && id.equals(((Proyeccion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proyeccion{" +
            "id=" + getId() +
            ", horaProyeccion='" + getHoraProyeccion() + "'" +
            ", estado='" + isEstado() + "'" +
            ", fechaComienzo='" + getFechaComienzo() + "'" +
            ", fechaFinalizacion='" + getFechaFinalizacion() + "'" +
            "}";
    }
}
