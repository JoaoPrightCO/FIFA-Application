package com.aroska.fifa.requests.dtos.administration.core;

import lombok.Data;

@Data
public class ZonaDto {

	private Integer id;
	private String zona;
	
	public String[] getValues() {
    	String[] values = new String[1];
    	
    	values[0] = getZona();
    	
    	return values;
    }
}
