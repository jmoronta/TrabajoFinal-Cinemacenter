package ar.edu.um.programacion2.cinemacenter.service;

import ar.edu.um.programacion2.cinemacenter.domain.Proyeccion;


public class CantidadProyeccion implements Comparable <CantidadProyeccion>{
	
	
	public CantidadProyeccion( Proyeccion proyeccion) {
		this.cantidad = 1L;
		this.proyeccion = proyeccion;
	}

	Long cantidad ;
	Proyeccion proyeccion;
	
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	public Proyeccion getProyeccion() {
		return proyeccion;
	}
	public void setProyeccion(Proyeccion proyeccion) {
		this.proyeccion = proyeccion;
	}
	
	public void sumarcantidad() {
		this.cantidad =  cantidad + 1;
	}
	@Override
	public int compareTo(CantidadProyeccion arg0) {
		// TODO Auto-generated method stub
		return (int)(arg0.getCantidad() - getCantidad() );
	}
	
}

