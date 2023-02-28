package br.com.alura.school.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserEnrollsReportResponse {

    @JsonProperty
    private final String email;
    
    @JsonProperty("quantidade_matriculas")
    private final Long quantidadeMatriculas;
    
    public UserEnrollsReportResponse(String email, Long quantidadeMatriculas) {
		this.email = email;
		this.quantidadeMatriculas = quantidadeMatriculas;
	}
}
