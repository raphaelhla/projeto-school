package br.com.alura.school.enroll;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollUserReportResponse {

    @JsonProperty
    private final String email;
    
    @JsonProperty("quantidade_matriculas")
    private final Long amountEnroll;
    
    public EnrollUserReportResponse(String email, Long amountEnroll) {
		this.email = email;
		this.amountEnroll = amountEnroll;
	}
}
