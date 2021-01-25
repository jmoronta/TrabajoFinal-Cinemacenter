package ar.edu.um.programacion2.cinemacenter.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pelicula.class)
public abstract class Pelicula_ {

	public static volatile SingularAttribute<Pelicula, String> descripcion;
	public static volatile SingularAttribute<Pelicula, Boolean> estado;
	public static volatile SetAttribute<Pelicula, Proyeccion> proyeccions;
	public static volatile SingularAttribute<Pelicula, String> genero;
	public static volatile SingularAttribute<Pelicula, Integer> duracion;
	public static volatile SingularAttribute<Pelicula, LocalDate> fechaComienzo;
	public static volatile SingularAttribute<Pelicula, Long> id;
	public static volatile SingularAttribute<Pelicula, String> clasifiacion;
	public static volatile SingularAttribute<Pelicula, String> nombre;
	public static volatile SingularAttribute<Pelicula, String> detalle;
	public static volatile SingularAttribute<Pelicula, LocalDate> fechaFinalizacion;

	public static final String DESCRIPCION = "descripcion";
	public static final String ESTADO = "estado";
	public static final String PROYECCIONS = "proyeccions";
	public static final String GENERO = "genero";
	public static final String DURACION = "duracion";
	public static final String FECHA_COMIENZO = "fechaComienzo";
	public static final String ID = "id";
	public static final String CLASIFIACION = "clasifiacion";
	public static final String NOMBRE = "nombre";
	public static final String DETALLE = "detalle";
	public static final String FECHA_FINALIZACION = "fechaFinalizacion";

}

