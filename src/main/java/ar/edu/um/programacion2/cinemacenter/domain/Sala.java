package ar.edu.um.programacion2.cinemacenter.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sala.
 */
@Entity
@Table(name = "sala")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "filas")
    private Integer filas;

    @Column(name = "asientos")
    private Integer asientos;

    @OneToMany(mappedBy = "sala")
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

    public Sala nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstado() {
        return estado;
    }

    public Sala estado(Integer estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getFilas() {
        return filas;
    }

    public Sala filas(Integer filas) {
        this.filas = filas;
        return this;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    public Integer getAsientos() {
        return asientos;
    }

    public Sala asientos(Integer asientos) {
        this.asientos = asientos;
        return this;
    }

    public void setAsientos(Integer asientos) {
        this.asientos = asientos;
    }

    public Set<Proyeccion> getProyeccions() {
        return proyeccions;
    }

    public Sala proyeccions(Set<Proyeccion> proyeccions) {
        this.proyeccions = proyeccions;
        return this;
    }

    public Sala addProyeccion(Proyeccion proyeccion) {
        this.proyeccions.add(proyeccion);
        proyeccion.setSala(this);
        return this;
    }

    public Sala removeProyeccion(Proyeccion proyeccion) {
        this.proyeccions.remove(proyeccion);
        proyeccion.setSala(null);
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
        if (!(o instanceof Sala)) {
            return false;
        }
        return id != null && id.equals(((Sala) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sala{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", estado=" + getEstado() +
            ", filas=" + getFilas() +
            ", asientos=" + getAsientos() +
            "}";
    }
}
