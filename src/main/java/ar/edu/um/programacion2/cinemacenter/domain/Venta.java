package ar.edu.um.programacion2.cinemacenter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @ManyToOne
    @JsonIgnoreProperties(value = "ventas", allowSetters = true)
    private Butaca butaca;

    @ManyToOne
    @JsonIgnoreProperties(value = "ventas", allowSetters = true)
    private Proyeccion proyeccion;

    @ManyToOne
    @JsonIgnoreProperties(value = "ventas", allowSetters = true)
    private Pelicula pelicula;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public Venta fechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
        return this;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Butaca getButaca() {
        return butaca;
    }

    public Venta butaca(Butaca butaca) {
        this.butaca = butaca;
        return this;
    }

    public void setButaca(Butaca butaca) {
        this.butaca = butaca;
    }

    public Proyeccion getProyeccion() {
        return proyeccion;
    }

    public Venta proyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
        return this;
    }

    public void setProyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Venta pelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        return this;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return id != null && id.equals(((Venta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            "}";
    }
}
