package br.com.alura.school.matricula;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewMatriculaRequest {
    
    @Size(max=20)
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

