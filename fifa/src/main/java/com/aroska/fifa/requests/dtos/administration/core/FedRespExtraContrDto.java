package com.aroska.fifa.requests.dtos.administration.core;

import lombok.Data;

@Data
public class FedRespExtraContrDto {

	private Integer id;
	private String entidade;
	private String descricao;
	
	public String[] getValues() {
    	String[] values = new String[2];
    	
    	values[0] = getEntidade();
    	values[1] = getDescricao();
    	
    	return values;
    }
}
