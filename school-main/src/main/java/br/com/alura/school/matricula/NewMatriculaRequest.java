package br.com.alura.school.matricula;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

class NewMatriculaRequest {
    
    @Size(max=20)
    @NotBlank
    @JsonProperty
	private String username;

    String getUsername() {
        return username;
    }

	void setUsername(String username) {
		this.username = username;
	}
}

