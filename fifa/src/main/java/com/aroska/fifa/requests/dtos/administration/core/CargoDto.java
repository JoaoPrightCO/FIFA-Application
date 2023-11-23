package com.aroska.fifa.requests.dtos.administration.core;

import lombok.Data;

@Data
public class CargoDto {

	private Integer id;
	private String cargo;
	
	public String[] getValues() {
    	String[] values = new String[1];
    	
    	values[0] = getCargo();
    	
    	return values;
    }
}
