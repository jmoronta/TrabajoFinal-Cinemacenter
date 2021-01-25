package ar.edu.um.programacion2.cinemacenter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Butaca.
 */
@Entity
@Table(name = "butaca")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Butaca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @Column(name = "fila")
    private Integer fila;

    @Column(name = "asiento")
    private Integer asiento;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToOne
    @JsonIgnoreProperties(value = "butacas", allowSetters = true)
    private Proyeccion proyeccion;

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

    public Butaca fechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
        return this;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Integer getFila() {
        return fila;
    }

    public Butaca fila(Integer fila) {
        this.fila = fila;
        return this;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getAsiento() {
        return asiento;
    }

    public Butaca asiento(Integer asiento) {
        this.asiento = asiento;
        return this;
    }

    public void setAsiento(Integer asiento) {
        this.asiento = asiento;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Butaca estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Proyeccion getProyeccion() {
        return proyeccion;
    }

    public Butaca proyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
        return this;
    }

    public void setProyeccion(Proyeccion proyeccion) {
        this.proyeccion = proyeccion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Butaca)) {
            return false;
        }
        return id != null && id.equals(((Butaca) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Butaca{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", fila=" + getFila() +
            ", asiento=" + getAsiento() +
            ", estado='" + isEstado() + "'" +
            "}";
    }
}
