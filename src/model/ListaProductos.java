package model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaProductos {

	@JsonProperty("id_producto")
	@NotNull(message = "id_producto no puede ser nulo")
	private int id_producto;
	
	@JsonProperty("cantidad")
	@NotNull(message = "cantidad no puede ser nulo")
	private int cantidad;
	
	@JsonProperty("precio")
	@NotNull(message = "precio no puede ser nulo")
	private double precio;

	@JsonCreator
	public ListaProductos( 
			@JsonProperty("id_producto") final int id_producto, 
			@JsonProperty("cantidad") final int cantidad, 
			@JsonProperty("precio") final double precio) {
		super();
		this.id_producto = id_producto;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	@JsonProperty("id_producto")
	public int getId_producto() {
		return id_producto;
	}
	@JsonProperty("id_producto")
	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}
	
	@JsonProperty("cantidad")
	public int getCantidad() {
		return cantidad;
	}
	@JsonProperty("cantidad")
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@JsonProperty("precio")
	public double getPrecio() {
		return precio;
	}
	@JsonProperty("precio")
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
