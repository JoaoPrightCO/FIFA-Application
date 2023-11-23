package com.aroska.fifa.requests.dtos.administration.core;

import lombok.Data;

@Data
public class NacionalidadeDto {

	private Integer id;
	private String nomeCurto;
	private String nomeExt;
	
	public String[] getValues() {
    	String[] values = new String[2];
    	
    	values[0] = getNomeCurto();
    	values[1] = getNomeExt();
    	
    	return values;
    }
}
