package model;

import java.sql.Date;
import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pedido {

	@JsonProperty("id_usuario")
	@NotNull(message = "id_usuario no puede ser nulo")
	private String id_usuario;
	
	@JsonProperty("id_listaproducto")
	@NotNull(message = "id_listaproducto no puede ser nulo")
	private String id_listaproducto;
	
	@JsonProperty("fecha_creacion")
	private Date fecha_creacion;
	
	@JsonProperty("estado")
	@NotNull(message = "estado no puede ser nulo")
	private int estado;
	
	@JsonProperty("cantidad_productos")
	@NotNull(message = "cantidad_productos no puede ser nulo")
	private int cantidad_productos;
	
	@JsonProperty("precio_final")
	@NotNull(message = "precio_final no puede ser nulo")
	private double precio_final;
	
	@JsonProperty("listaProductos")
	private ArrayList<ListaProductos> listaProductos;

	@JsonCreator
	public Pedido( 
			@JsonProperty("id_usuario") final String id_usuario, 
			@JsonProperty("id_listaproducto") final String id_listaproducto, 
			@JsonProperty("fecha_creacion") final Date fecha_creacion, 
			@JsonProperty("estado") final int estado,
			@JsonProperty("cantidad_productos") final int cantidad_productos, 
			@JsonProperty("precio_final") final double precio_final, 
			@JsonProperty("listaProductos") final ArrayList<ListaProductos> listaProductos) {
		super();
		this.id_usuario = id_usuario;
		this.id_listaproducto = id_listaproducto;
		this.fecha_creacion = fecha_creacion;
		this.estado = estado;
		this.cantidad_productos = cantidad_productos;
		this.precio_final = precio_final;
		this.listaProductos = listaProductos;
	}

	@JsonProperty("id_usuario")
	public String getId_usuario() {
		return id_usuario;
	}
	@JsonProperty("id_usuario")
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	@JsonProperty("id_listaproducto")
	public String getId_listaproducto() {
		return id_listaproducto;
	}
	@JsonProperty("id_listaproducto")
	public void setId_listaproducto(String id_listaproducto) {
		this.id_listaproducto = id_listaproducto;
	}

	@JsonProperty("fecha_creacion")
	public Date getFecha_creacion() {
		return fecha_creacion;
	}
	@JsonProperty("fecha_creacion")
	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	@JsonProperty("estado")
	public int getEstado() {
		return estado;
	}
	@JsonProperty("estado")
	public void setEstado(int estado) {
		this.estado = estado;
	}

	@JsonProperty("cantidad_productos")
	public int getCantidad_productos() {
		return cantidad_productos;
	}
	@JsonProperty("cantidad_productos")
	public void setCantidad_productos(int cantidad_productos) {
		this.cantidad_productos = cantidad_productos;
	}

	@JsonProperty("precio_final")
	public double getPrecio_final() {
		return precio_final;
	}
	@JsonProperty("precio_final")
	public void setPrecio_final(double precio_final) {
		this.precio_final = precio_final;
	}

	@JsonProperty("listaProductos")
	public ArrayList<ListaProductos> getListaProductos() {
		return listaProductos;
	}
	@JsonProperty("listaProductos")
	public void setListaProductos(ArrayList<ListaProductos> listaProductos) {
		this.listaProductos = listaProductos;
	}

}
