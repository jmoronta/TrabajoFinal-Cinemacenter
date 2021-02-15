package ar.edu.um.programacion2.cinemacenter.service;

import ar.edu.um.programacion2.cinemacenter.domain.Pelicula;


public class CantidadVentasPelicula implements Comparable <CantidadVentasPelicula>{
	
	
	public CantidadVentasPelicula( Pelicula pelicula) {
		this.cantidad = 1;
		this.pelicula = pelicula;
	}
	
	public CantidadVentasPelicula(Pelicula pelicula, int cantidad) {
		this.cantidad = cantidad;
		this.pelicula = pelicula;
	}



	public Pelicula getPelicula() {
		return pelicula;
	}
	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	int cantidad ;
	Pelicula pelicula;
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	public void sumarcantidad() {
		this.cantidad =  cantidad + 1;
	}
	@Override
	public int compareTo(CantidadVentasPelicula arg0) {
		// TODO Auto-generated method stub
		return (int)(arg0.getCantidad() - getCantidad() );
	}
	
}

