package model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Empresa extends Usuario{
	
	@JsonProperty("cif")
	@NotNull(message = "cif no puede ser nulo")
	private String cif;
	@JsonProperty("nombre_fiscal")
	@NotNull(message = "nombre_fiscal no puede ser nulo")
	private String nombre_fiscal;
	@JsonProperty("nombre_comercial")
	@NotNull(message = "nombre_comercial no puede ser nulo")
	private String nombre_comercial;
	
	@JsonCreator
	public Empresa(@JsonProperty("id") final String id, @JsonProperty("username") final String username, @JsonProperty("pass") final String pass, 
			@JsonProperty("email") final String email, @JsonProperty("nombre") final String nombre, @JsonProperty("apellidos") final String apellidos,
			@JsonProperty("direccion") final String direccion, @JsonProperty("direccion2") final String direccion2, @JsonProperty("telefono") final String telefono, 
			@JsonProperty("tipo") final int tipo, @JsonProperty("cif") final String cif,@JsonProperty("nombre_fiscal") final String nombre_fiscal,
			@JsonProperty("nombre_comercial") final String nombre_comercial) {
		super(id, username, pass, email, nombre, apellidos, direccion, direccion2, telefono, tipo);
		this.cif = cif;
		this.nombre_fiscal = nombre_fiscal;
		this.nombre_comercial = nombre_comercial;
	}
	
	@JsonProperty("cif")
	public String getCif() {
		return cif;
	}
	@JsonProperty("cif")
	public void setCif(String cif) {
		this.cif = cif;
	}
	
	@JsonProperty("nombre_fiscal")
	public String getNombreFiscal() {
		return nombre_fiscal;
	}
	@JsonProperty("nombre_fiscal")
	public void setNombreFiscal(String nombre_fiscal) {
		this.nombre_fiscal = nombre_fiscal;
	}
	
	@JsonProperty("nombre_comercial")
	public String getNombreComercial() {
		return nombre_comercial;
	}
	@JsonProperty("nombre_comercial")
	public void setNombreComercial(String nombre_comercial) {
		this.nombre_comercial = nombre_comercial;
	}
	
}
