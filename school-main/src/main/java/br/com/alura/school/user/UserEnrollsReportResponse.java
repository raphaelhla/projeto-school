package br.com.alura.school.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserEnrollsReportResponse implements Comparable<UserEnrollsReportResponse>{

    @JsonProperty
    private final String email;
    
    @JsonProperty("quantidade_matriculas")
    private final int quantidadeMatriculas;

    public UserEnrollsReportResponse(User user) {
        this.email = user.getEmail();
        this.quantidadeMatriculas = user.getQuantidadeMatriculas();
    }
    
	public int getQuantidadeMatriculas() {
		return quantidadeMatriculas;
	}

	@Override
	public int compareTo(UserEnrollsReportResponse o) {
		return this.quantidadeMatriculas - o.getQuantidadeMatriculas();
	}
}
