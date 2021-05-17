package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

	@JsonProperty("id")
	private String id;
	@JsonProperty("token")
	private String token;

	public Token(@JsonProperty("token") final String token, @JsonProperty("id") final String id) {
		this.token = token;
		this.id = id;
	}
	
	@JsonProperty("token")
	public String getToken() {
		return token;
	}
	@JsonProperty("key")
	public void setToken(String token) {
		this.token = token;
	}
	
	@JsonProperty("id")
	public String getId() {
		return id;
	}
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}
	
	
}
