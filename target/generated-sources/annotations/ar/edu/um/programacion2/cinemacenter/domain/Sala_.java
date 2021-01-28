package ar.edu.um.programacion2.cinemacenter.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sala.class)
public abstract class Sala_ {

	public static volatile SingularAttribute<Sala, Boolean> estado;
	public static volatile SetAttribute<Sala, Proyeccion> proyeccions;
	public static volatile SingularAttribute<Sala, Integer> filas;
	public static volatile SingularAttribute<Sala, Integer> asientos;
	public static volatile SingularAttribute<Sala, Long> id;
	public static volatile SetAttribute<Sala, Butaca> butacas;
	public static volatile SingularAttribute<Sala, String> nombre;

	public static final String ESTADO = "estado";
	public static final String PROYECCIONS = "proyeccions";
	public static final String FILAS = "filas";
	public static final String ASIENTOS = "asientos";
	public static final String ID = "id";
	public static final String BUTACAS = "butacas";
	public static final String NOMBRE = "nombre";

}

