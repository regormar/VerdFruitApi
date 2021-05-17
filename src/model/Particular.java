package model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Particular extends Usuario{

	@JsonProperty("dni")
	@NotNull(message = "dni no puede ser nulo")
	private String dni;
	
	@JsonCreator
	public Particular(@JsonProperty("id") final String id, @JsonProperty("username") final String username, @JsonProperty("pass") final String pass, 
			@JsonProperty("email") final String email, @JsonProperty("nombre") final String nombre, @JsonProperty("apellidos") final String apellidos,
			@JsonProperty("direccion") final String direccion, @JsonProperty("direccion2") final String direccion2, @JsonProperty("telefono") final String telefono, @JsonProperty("tipo") final int tipo, 
			@JsonProperty("dni") final String dni) {
		super(id, username, pass, email, nombre, apellidos, direccion, direccion2, telefono, tipo);
		this.dni = dni;
	}
	@JsonProperty("dni")
	public String getDni() {
		return dni;
	}
	@JsonProperty("dni")
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	

}
