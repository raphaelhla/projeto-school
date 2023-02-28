package br.com.alura.school.matricula;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatriculaDTO {
    
    @NotBlank
    @JsonProperty
	private String username;

    public String getUsername() {
        return username;
    }

	public void setUsername(String username) {
		this.username = username;
	}
}

