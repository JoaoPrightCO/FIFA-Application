package com.aroska.fifa.requests.dtos.administration.core;

import lombok.Data;

@Data
public class CamaraDto {

	private Integer id;
    private String localizacao;
    private String localizacaoCam;
    
    public String[] getValues() {
    	String[] values = new String[2];
    	
    	values[0] = getLocalizacao();
    	values[1] = getLocalizacaoCam();
    	
    	return values;
    }
    
}
