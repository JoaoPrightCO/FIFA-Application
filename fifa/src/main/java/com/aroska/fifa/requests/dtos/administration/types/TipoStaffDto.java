package com.aroska.fifa.requests.dtos.administration.types;

import lombok.Data;

@Data
public class TipoStaffDto {

	private Integer id;
	private String nome_curto;
	private String nome_ext;

	public String[] getValues() {
    	String[] values = new String[3];
    	
    	values[0] = String.valueOf(getId());
    	values[1] = getNome_curto();
    	values[2] = getNome_ext();
    	
    	return values;
    }
}
