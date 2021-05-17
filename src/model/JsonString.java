package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonString {
	
	@JsonProperty("key")
	private String key;

	public JsonString(@JsonProperty("key") final String key) {
		this.key = key;
	}
	
	@JsonProperty("key")
	public String getKey() {
		return key;
	}
	@JsonProperty("key")
	public void setKey(String key) {
		this.key = key;
	}
	
}
