package model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Producto {
	
	@JsonProperty("id_producto")
	private int id_producto;
	
	@JsonProperty("nombre_producto")
	private String nombre_producto;
	
	@JsonProperty("descripcion")
	private String descripcion;
	
	@JsonProperty("origen")
	private String origen;
	
	@JsonProperty("familia")
	private String familia;
	
	@JsonProperty("marca")
	private String marca;
	
	@JsonProperty("precio")
	private double precio;
	
	@JsonProperty("tipo_producto")
	private int tipo_producto;
	
	@JsonProperty("stock")
	private int stock;
	
	@JsonProperty("img")
	private String img;
	
	@JsonProperty("mas_vendido")
	private boolean mas_vendido;
	
	@JsonProperty("nuevo")
	private boolean nuevo;
	
	
	@JsonCreator
	public Producto(@JsonProperty("id_producto") final int id_producto, 
			@JsonProperty("nombre_proucto") final String nombre_producto, 
			@JsonProperty("descripcion") final String descripcion,
			@JsonProperty("origen") final String origen,
			@JsonProperty("familia") final String familia,
			@JsonProperty("marca") final String marca,
			@JsonProperty("precio") final double precio,
			@JsonProperty("tipo_producto") final int tipo_producto,
			@JsonProperty("stock") final int stock,
			@JsonProperty("img") final String img,
			@JsonProperty("mas_vendido") final boolean mas_vendido,
			@JsonProperty("nuevo") final boolean nuevo) {
		this.id_producto = id_producto;
		this.nombre_producto = nombre_producto;
		this.descripcion = descripcion;
		this.origen = origen;
		this.familia = familia;
		this.marca = marca;
		this.precio = precio;
		this.tipo_producto = tipo_producto;
		this.stock = stock;
		this.img = img;
		this.mas_vendido = mas_vendido;
		this.nuevo = nuevo;
		
	}


	@JsonProperty("id_producto")
	public int getId_producto() {
		return id_producto;
	}

	@JsonProperty("id_producto")
	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	@JsonProperty("nombre_producto")
	public String getNombre_producto() {
		return nombre_producto;
	}

	@JsonProperty("nombre_producto")
	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	@JsonProperty("descripcion")
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@JsonProperty("origen")
	public String getOrigen() {
		return origen;
	}

	@JsonProperty("origen")
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@JsonProperty("familia")
	public String getFamilia() {
		return familia;
	}

	@JsonProperty("familia")
	public void setFamilia(String familia) {
		this.familia = familia;
	}

	@JsonProperty("marca")
	public String getMarca() {
		return marca;
	}

	@JsonProperty("marca")
	public void setMarca(String marca) {
		this.marca = marca;
	}

	@JsonProperty("precio")
	public double getPrecio() {
		return precio;
	}

	@JsonProperty("precio")
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@JsonProperty("tipo_producto")
	public int getTipo_producto() {
		return tipo_producto;
	}

	@JsonProperty("tipo_producto")
	public void setTipo_producto(int tipo_producto) {
		this.tipo_producto = tipo_producto;
	}

	@JsonProperty("stock")
	public int getStock() {
		return stock;
	}

	@JsonProperty("stock")
	public void setStock(int stock) {
		this.stock = stock;
	}

	@JsonProperty("img")
	public String getImg() {
		return img;
	}

	@JsonProperty("img")
	public void setImg(String img) {
		this.img = img;
	}

	@JsonProperty("mas_vendido")
	public boolean isMas_vendido() {
		return mas_vendido;
	}

	@JsonProperty("mas_vendido")
	public void setMas_vendido(boolean mas_vendido) {
		this.mas_vendido = mas_vendido;
	}

	@JsonProperty("nuevo")
	public boolean isNuevo() {
		return nuevo;
	}

	@JsonProperty("nuevo")
	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}
	

}
