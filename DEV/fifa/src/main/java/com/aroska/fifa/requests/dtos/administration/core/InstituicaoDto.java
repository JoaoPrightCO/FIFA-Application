package com.aroska.fifa.requests.dtos.administration.core;

import lombok.Data;

@Data
public class InstituicaoDto {

	private Integer id;
	private String nome;
	private String tipo;
	
	public String[] getValues() {
    	String[] values = new String[2];
    	
    	values[0] = getNome();
    	values[1] = getTipo();
    	
    	return values;
    }
}
