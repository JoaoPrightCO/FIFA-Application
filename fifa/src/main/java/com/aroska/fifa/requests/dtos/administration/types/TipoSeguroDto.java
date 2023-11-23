package com.aroska.fifa.requests.dtos.administration.types;

import lombok.Data;

@Data
public class TipoSeguroDto {

	private Integer id;
	private String tipo_seguro;


	public String[] getValues() {
    	String[] values = new String[2];
    	
    	values[0] = String.valueOf(getId());
    	values[1] = getTipo_seguro();
    	
    	return values;
    }
}
