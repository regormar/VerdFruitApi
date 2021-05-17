package model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {
	
	@JsonProperty("id")
	private String id;
	@JsonProperty("username")
	@NotNull(message = "username no puede ser nulo")
	private String username;
	@JsonProperty("pass")
	@NotNull(message = "pass no puede ser nulo")
	private String pass;
	@JsonProperty("email")
	@NotNull(message = "email no puede ser nulo")
	private String email;
	@JsonProperty("nombre")
	@NotNull(message = "nombre no puede ser nulo")
	private String nombre;
	@JsonProperty("apellidos")
	@NotNull(message = "apellidos no puede ser nulo")
	private String apellidos;
	@JsonProperty("direccion")
	@NotNull(message = "direccion no puede ser nulo")
	private String direccion;
	@JsonProperty("direccion2")
	private String direccion2;
	@JsonProperty("telefono")
	@NotNull(message = "telefono no puede ser nulo")
	private String telefono;
	@JsonProperty("tipo")
	@NotNull(message = "tipo no puede ser nulo")
	private int tipo;
	
	public Usuario(
			final String id, 
			final String username, 
			final String pass, 
			final String email, 
			final String nombre, 
			final String apellidos,
			final String direccion, 
			final String direccion2, 
			final String telefono, 
			final int tipo) {
		this.id = id;
		this.username = username;
		this.pass = pass;
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.direccion2 = direccion2;
		this.telefono = telefono;
		this.tipo = tipo;
	}
	
	@JsonProperty("id")
	public String getId() {
		return id;
	}
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}
	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonProperty("pass")
	public String getPass() {
		return pass;
	}
	@JsonProperty("pass")
	public void setPass(String pass) {
		this.pass = pass;
	}
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	@JsonProperty("nombre")
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@JsonProperty("apellidos")
	public String getApellidos() {
		return apellidos;
	}
	@JsonProperty("apellidos")
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	@JsonProperty("direccion")
	public String getDireccion() {
		return direccion;
	}
	@JsonProperty("direccion")
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	@JsonProperty("direccion2")
	public String getDireccion2() {
		return direccion2;
	}
	@JsonProperty("direccion2")
	public void setDireccion2(String direccion) {
		this.direccion2 = direccion;
	}
	@JsonProperty("telefono")
	public String getTelefono() {
		return telefono;
	}
	@JsonProperty("telefono")
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	@JsonProperty("tipo")
	public int getTipo() {
		return tipo;
	}
	@JsonProperty("tipo")
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	
	
}
