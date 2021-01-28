package ar.edu.um.programacion2.cinemacenter.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Venta.class)
public abstract class Venta_ {

	public static volatile SingularAttribute<Venta, Pelicula> pelicula;
	public static volatile SingularAttribute<Venta, Proyeccion> proyeccion;
	public static volatile SingularAttribute<Venta, Long> id;
	public static volatile SingularAttribute<Venta, LocalDate> fechaVenta;
	public static volatile SingularAttribute<Venta, Butaca> butaca;

	public static final String PELICULA = "pelicula";
	public static final String PROYECCION = "proyeccion";
	public static final String ID = "id";
	public static final String FECHA_VENTA = "fechaVenta";
	public static final String BUTACA = "butaca";

}

