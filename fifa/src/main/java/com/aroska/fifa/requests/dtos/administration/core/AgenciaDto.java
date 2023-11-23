package com.aroska.fifa.requests.dtos.administration.core;

import lombok.Data;

@Data
public class AgenciaDto {

	private Integer id;
	private String agencia;
	
	public String[] getValues() {
    	String[] values = new String[1];
    	
    	values[0] = getAgencia();
    	
    	return values;
    }
}
