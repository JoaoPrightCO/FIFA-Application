package com.aroska.fifa.requests.dtos.elearning;

import lombok.Data;

@Data
public class QuizDto {

	private Integer id;
	private Integer id_sessao;
	private String descricao;
	
    public String[] getValues() {
    	String[] values = new String[2];
    	
    	values[0] = String.valueOf(getId_sessao());
    	values[1] = String.valueOf(getDescricao());
    	
    	return values;
    }
	
}
