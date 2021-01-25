package ar.edu.um.programacion2.cinemacenter.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Butaca.class)
public abstract class Butaca_ {

	public static volatile SingularAttribute<Butaca, Boolean> estado;
	public static volatile SingularAttribute<Butaca, Proyeccion> proyeccion;
	public static volatile SingularAttribute<Butaca, Integer> fila;
	public static volatile SingularAttribute<Butaca, Long> id;
	public static volatile SingularAttribute<Butaca, Integer> asiento;
	public static volatile SingularAttribute<Butaca, LocalDate> fechaVenta;

	public static final String ESTADO = "estado";
	public static final String PROYECCION = "proyeccion";
	public static final String FILA = "fila";
	public static final String ID = "id";
	public static final String ASIENTO = "asiento";
	public static final String FECHA_VENTA = "fechaVenta";

}

