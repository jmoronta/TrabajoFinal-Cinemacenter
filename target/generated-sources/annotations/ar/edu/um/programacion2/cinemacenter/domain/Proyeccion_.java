package ar.edu.um.programacion2.cinemacenter.domain;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Proyeccion.class)
public abstract class Proyeccion_ {

	public static volatile SingularAttribute<Proyeccion, Boolean> estado;
	public static volatile SingularAttribute<Proyeccion, Pelicula> pelicula;
	public static volatile SingularAttribute<Proyeccion, Sala> sala;
	public static volatile SetAttribute<Proyeccion, Venta> ventas;
	public static volatile SingularAttribute<Proyeccion, LocalDate> fechaComienzo;
	public static volatile SingularAttribute<Proyeccion, Long> id;
	public static volatile SingularAttribute<Proyeccion, ZonedDateTime> horaProyeccion;
	public static volatile SetAttribute<Proyeccion, Butaca> butacas;
	public static volatile SingularAttribute<Proyeccion, LocalDate> fechaFinalizacion;

	public static final String ESTADO = "estado";
	public static final String PELICULA = "pelicula";
	public static final String SALA = "sala";
	public static final String VENTAS = "ventas";
	public static final String FECHA_COMIENZO = "fechaComienzo";
	public static final String ID = "id";
	public static final String HORA_PROYECCION = "horaProyeccion";
	public static final String BUTACAS = "butacas";
	public static final String FECHA_FINALIZACION = "fechaFinalizacion";

}

